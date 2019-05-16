import org.jgrapht.graph.SimpleWeightedGraph;
import java.util.List;

public class Main {

    public static void main(String[] args){

        //Take country data file and import cities into list (contains city, country, lat & lng, population)
        List<City> cityList = CSVHelper.cityListByCountryName("mali_minimal_10");
        //Create a simple weighted graph with data
        SimpleWeightedGraph graph = GraphHelper.createWeightedGraph(cityList);
        //Export graph file to Resources/test.csv
        CSVHelper.exportGraphToCSV(graph, "test");

    }


}
