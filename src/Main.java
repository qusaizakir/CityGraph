import org.jgrapht.graph.SimpleWeightedGraph;
import java.util.ArrayList;
import java.util.List;

public class Main{

    private static OpenRouteApiWrapper apiWrapper;
    private static List<City> cityList;

    public static void main(String[] args){

        //TODO SimpleMaps has formatted url for downloading a countries map
        //Use the format "https://simplemaps.com/static/data/country-cities/gb/gb.csv" to download a countries .csv data file
        //TODO use opencsv to delete irrelevant cols and cities under a specific population <10k
        //TODO use openstreetmaps to find routes between cities.

        //TODO use the matrix endpoint rather than the directions endpoint (too many directions requests)
        //TODO graph is undirected so export graph removes all duplicates (A -> B == B -> A) account for this?

        //Response from API callbacks
        apiWrapper = new OpenRouteApiWrapper(new OpenRouteAPICallbacks() {
            @Override
            public void OnSummarySuccess(Summary summary) {
                System.out.println(summary.toString());
            }

            @Override
            public void OnMatrixSuccess(Matrix matrix) {
                //Create a graph from the distance matrix and export graph to .csv
                SimpleWeightedGraph drivingGraph = GraphHelper.createWeightedGraphDrivingDistance(cityList, matrix.getDistances());
                CSVHelper.exportGraphToCSV(drivingGraph, "mali_driving_distance");
            }
        });

        //Take country data file and import cities into a list (contains city, country, lat & lng, population)
        cityList = CSVHelper.cityListByCountryName("mali_minimal_10");

        //------ Test method that uses straight line distance ------
        straightLineDistanceGraph(cityList);

        //------ Test method that retrieves directions data from API ------
        testDirectionAPI(cityList);

        //------ Method that retrieves Matrix data from API from given CityList ------
        distanceMatrixAPI(cityList);

    }

    private static void straightLineDistanceGraph(List<City> cityList) {
        //Create a simple weighted graph with data (StraightLine Distance)
        //Export graph file to Resources/test.csv*/
        SimpleWeightedGraph strGraph = GraphHelper.createWeightedGraphStraightLineDistance(cityList);
        CSVHelper.exportGraphToCSV(strGraph, "mali_str_distance");
    }

    private static void distanceMatrixAPI(List<City> cityList){

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
    }

    private static void testDirectionAPI(List<City> cityList) {
        apiWrapper.getDistanceByCar(cityList.get(0), cityList.get(1));
    }
}
