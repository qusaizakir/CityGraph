import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args){

        //TODO SimpleMaps has formatted url for downloading a countries map
        //Use the format "https://simplemaps.com/static/data/country-cities/gb/gb.csv" to download a countries .csv data file
        //TODO use opencsv to delete irrelevant cols and cities under a specific population <10k
        //TODO use openstreetmaps to find routes between cities.

        //TODO use the matrix endpoint rather than the directions endpoint (too many directions requests)
        //TODO graph is undirected so export graph removes all duplicates (A -> B == B -> A) account for this?

        //Take country data file and import cities into list (contains city, country, lat & lng, population)
        List<City> cityList = CSVHelper.cityListByCountryName("mali_minimal_10");
        //Create a simple weighted graph with data
        SimpleWeightedGraph graph = GraphHelper.createWeightedGraphStraightLineDistance(cityList);
        //Export graph file to Resources/test.csv
        CSVHelper.exportGraphToCSV(graph, "test");


        //Test implementation of OpenRouteService to give directions (distance and duration) from two cites.
        OpenRouteApiWrapper apiWrapper = new OpenRouteApiWrapper(new SummaryCallbacks(){
            @Override
            public void OnSummarySuccess(Summary summary) {
                System.out.println(summary.toString());
            }
        });

        apiWrapper.getDistanceByCar(cityList.get(0), cityList.get(1));



    }
}
