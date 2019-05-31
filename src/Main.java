import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import java.util.ArrayList;
import java.util.List;

public class Main{

    private static OpenRouteApiWrapper apiWrapper;
    private static List<City> cityList;
    private static Matrix driveMatrix;
    private static Matrix footMatrix;

    public static void main(String[] args){

        //TODO SimpleMaps has formatted url for downloading a countries map
        //Use the format "https://simplemaps.com/static/data/country-cities/gb/gb.csv" to download a countries .csv data file
        //TODO use opencsv to delete irrelevant cols and cities under a specific population <10k
        //TODO use openstreetmaps to find routes between cities.
        //TODO use the matrix endpoint rather than the directions endpoint (too many directions requests)
        //TODO graph is undirected so export graph removes all duplicates (A -> B == B -> A) account for this?
        //TODO add multiple edges (one for driving and one for walking?)
        //TODO

        //Response from API callbacks
        apiWrapper = new OpenRouteApiWrapper(new OpenRouteAPICallbacks() {
            @Override
            public void OnSummarySuccess(Summary summary) {
                System.out.println(summary.toString());
            }

            @Override
            public void OnDriveMatrixSuccess(Matrix matrix) {
                //Create a graph from the distance matrix and export graph to .csv
                //SimpleWeightedGraph drivingGraph = GraphHelper.createWeightedGraphDrivingDistance(cityList, matrix.getDistances());
                //CSVHelper.exportGraphToCSV(drivingGraph, "mali_driving_distance");
                driveMatrix = matrix;
                System.out.println("Driving " + matrix.getDistances());
            }

            @Override
            public void OnFootMatrixSuccess(Matrix matrix) {
                footMatrix = matrix;
                System.out.println("Foot " + matrix.getDistances());
            }
        });

        //Take country data file and import cities into a list (contains city, country, lat & lng, population)
        cityList = CSVHelper.cityListByCountryName("mali_minimal_10");

        //------ Test method that uses straight line distance ------
        straightLineDistanceGraph(cityList);

        //------ Test method that retrieves directions data from API ------
        testDirectionAPI(cityList);

        //------ Method that retrieves Matrix data for driving and walking from API from given CityList ------
        driveAndFootDistanceMatrixAPI(cityList);


        boolean flag = true;

        while (flag) {
            System.out.println("Drive " + (driveMatrix == null));
            System.out.println("Foot " + (footMatrix == null));
            if (driveMatrix != null && footMatrix != null) {
                flag = false;
                WeightedMultigraph drivingGraph = GraphHelper.createWeightedGraphDrivingAndFootDistance(cityList, driveMatrix.getDistances(), footMatrix.getDistances());
                CSVHelper.exportGraphToCSV(drivingGraph, "mali_driving_foot_distance");
                System.out.println("Completed");
                System.exit(4);
            }
        }

    }

    private static void straightLineDistanceGraph(List<City> cityList) {
        //Create a simple weighted graph with data (StraightLine Distance)
        //Export graph file to Resources/test.csv*/
        SimpleWeightedGraph strGraph = GraphHelper.createWeightedGraphStraightLineDistance(cityList);
        CSVHelper.exportGraphToCSV(strGraph, "mali_str_distance");
    }

    private static void driveAndFootDistanceMatrixAPI(List<City> cityList){

        //Create a list of list of GPS to send as parameter to matrix endpoint
        List<List<Double>> cityGPSList = new ArrayList<>();
        for(City c: cityList){
            List<Double> list = new ArrayList<>();
            list.add(c.getLng());
            list.add(c.getLat());
            cityGPSList.add(list);
        }

        //Create the body for the POST request (GPS list, Distance, Units)
        GPSCityLocations gpsCityLocations = new GPSCityLocations(cityGPSList,GPSCityLocations.DISTANCE, GPSCityLocations.KM);
        //Send POST request with body
        apiWrapper.getMatrixDistanceByCar(gpsCityLocations);
        apiWrapper.getMatrixDistanceByFoot(gpsCityLocations);
    }

    private static void testDirectionAPI(List<City> cityList) {
        apiWrapper.getDistanceByCar(cityList.get(0), cityList.get(1));
    }
}
