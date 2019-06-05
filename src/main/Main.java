package main;
import main.OpenRouteAPI.MatrixBody;
import main.OpenRouteAPI.MatrixResponse;
import main.OpenRouteAPI.OpenRouteAPICallbacks;
import main.OpenRouteAPI.OpenRouteApiWrapper;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import java.util.ArrayList;
import java.util.List;

public class Main{

    private static OpenRouteApiWrapper apiWrapper;
    private static List<City> cityList;
    private static MatrixResponse driveMatrixResponse;
    private static MatrixResponse footMatrixResponse;

    public static void main(String[] args){

        //TODO SimpleMaps has formatted url for downloading a countries map
        //Use the format "https://simplemaps.com/static/data/country-cities/gb/gb.csv" to download a countries .csv data file
        //TODO use opencsv to delete irrelevant cols and cities under a specific population <10k
        //TODO

        //Response from API callbacks
        apiWrapper = new OpenRouteApiWrapper(new OpenRouteAPICallbacks() {
            @Override
            public void OnDriveMatrixSuccess(MatrixResponse matrixResponse) {
                //Create a graph from the distance matrixResponse and export graph to .csv
                //SimpleWeightedGraph drivingGraph = GraphHelper.createWeightedGraphDrivingDistance(cityList, matrixResponse.getDistances());
                //CSVHelper.exportGraphToDefaultCSVFormat(drivingGraph, "mali_driving_distance");
                driveMatrixResponse = matrixResponse;
                System.out.println("Driving " + matrixResponse.getDistances());
            }

            @Override
            public void OnFootMatrixSuccess(MatrixResponse matrixResponse) {
                footMatrixResponse = matrixResponse;
                System.out.println("Foot " + matrixResponse.getDistances());
            }
        });

        //Take country data file and import cities into a list (contains city, country, lat & lng, population)
        cityList = CSVHelper.cityListByCountryName("mali_minimal_10");

        //------ Test method that uses straight line distance ------
        //straightLineDistanceGraph(cityList);

        //------ Method that retrieves OpenRouteAPI.MatrixResponse data for driving and walking from API from given CityList ------
        driveAndFootDistanceMatrixAPI(cityList);


        while (true) {
            System.out.println("Drive " + (driveMatrixResponse == null));
            System.out.println("Foot " + (footMatrixResponse == null));
            if (driveMatrixResponse != null && footMatrixResponse != null) {
                WeightedMultigraph drivingGraph = GraphHelper.createWeightedGraphDrivingAndFootDistance(cityList, driveMatrixResponse.getDistances(), footMatrixResponse.getDistances());
                CSVHelper.exportGraphToCustomCSVFormat(drivingGraph, "mail_driving_foot_distance_custom_format");
                System.out.println("Completed");
                System.exit(4);
            }
        }
    }

    private static void straightLineDistanceGraph(List<City> cityList) {
        //Create a simple weighted graph with data (StraightLine Distance)
        //Export graph file to Resources/test.csv*/
        SimpleWeightedGraph strGraph = GraphHelper.createWeightedGraphStraightLineDistance(cityList);
        CSVHelper.exportGraphToDefaultCSVFormat(strGraph, "mali_str_distance");
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
        MatrixBody matrixBody = new MatrixBody(cityGPSList, MatrixBody.DISTANCE, MatrixBody.KM);
        //Send POST request with body
        apiWrapper.getMatrixDistanceByCar(matrixBody);
        apiWrapper.getMatrixDistanceByFoot(matrixBody);
    }
}
