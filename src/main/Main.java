package main;
import main.OpenRouteAPI.MatrixBody;
import main.OpenRouteAPI.MatrixResponse;
import main.OpenRouteAPI.OpenRouteApiWrapper;
import main.SimpleMaps.SimpleMapsWrapper;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.ArrayList;
import java.util.List;

public class Main{

    private static OpenRouteApiWrapper openRouteWrapper;
    private static SimpleMapsWrapper mapsWrapper;
    private static List<City> cityList;
    private static MatrixResponse driveMatrixResponse;
    private static MatrixResponse footMatrixResponse;
    private static String countryCode;
    private static String API_KEY;
    private static long START = 0;

    public static void main(String[] args){
        START = System.currentTimeMillis();

        //Set the commandline arguments as API_KEY CountryCode
        if(args != null && args.length == 2){
            API_KEY = args[0];
            countryCode = args[1];
        }else{
            System.out.println("Wrong arguments. Enter: API_KEY COUNTRY_CODE");
            System.exit(9);
        }

        //The country to create a graph for:
        //The maximum number of requests from OpenRoute is 2500 (50 x 50) per request
        //For any given country with n cites, the total routes is (n)(n-1)
        //Therefore the maximum number of cities is set at 50
        //CSVCityLimit class with trim the CSV based on it's given filters (pop & city limit)
        //Download CSV from SimpleMaps for countryCode (gb)
        openRouteWrapper = new OpenRouteApiWrapper(API_KEY);
        mapsWrapper = new SimpleMapsWrapper();
        mapsWrapper.downloadCountryCSVByCode(countryCode);

        //Take country data file and import cities into a list (contains city, country, lat & lng, population)
        //Use the CSVCityLimit class to select what filters to put on the CSV list (population above 10k)
        cityList = CSVHelper.cityListByCountryName(countryCode, new CSVCityLimit(10000));

        //------ Method that creates a graph of straight line distances ------
        straightLineDistanceGraph(cityList);

        //------ Method that retrieves OpenRouteAPI MatrixResponse data for driving and walking from API from given CityList ------
        driveAndFootDistanceMatrixAPISync(cityList);

        //Once both driving and foot matrix responses have been retrieved, create the locations graph
        if (driveMatrixResponse != null && footMatrixResponse != null) {
            WeightedMultigraph drivingGraph = GraphHelper.createWeightedGraphDrivingAndFootDistance(cityList, driveMatrixResponse.getDistances(), footMatrixResponse.getDistances());
            CSVHelper.exportGraphToCustomCSVFormat(drivingGraph, countryCode + "_locations");
            System.out.println("Completed");
            System.exit(4);

        }
    }

    private static void straightLineDistanceGraph(List<City> cityList) {
        //Create a simple weighted graph with data (StraightLine Distance)
        //Export graph file to Resources/test.csv*/
        SimpleWeightedGraph strGraph = GraphHelper.createWeightedGraphStraightLineDistance(cityList);
        CSVHelper.exportGraphToDefaultCSVFormat(strGraph, countryCode + "_straight");
    }

    private static void driveAndFootDistanceMatrixAPISync(List<City> cityList){

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
        driveMatrixResponse = openRouteWrapper.getMatrixDistanceByCar(matrixBody);
        System.out.println("Drive distances API request completed: " + ((System.currentTimeMillis() - START) /1000) + " seconds");
        footMatrixResponse = openRouteWrapper.getMatrixDistanceByFoot(matrixBody);
        System.out.println("Foot distances API request completed: " + ((System.currentTimeMillis() - START) /1000) + " seconds");
    }
}
