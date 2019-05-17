import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class GraphHelper {

    public static SimpleWeightedGraph createWeightedGraphStraightLineDistance(List<City> cityList){
        SimpleWeightedGraph<City, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Add each city as a vertex
        //Add each edge, with straight line distance between cities as weight
        for(City c: cityList){
            graph.addVertex(c);
        }

        int count = 0;

        for(int i=0; i<cityList.size(); i++){
            for (City city : cityList) {
                if (!cityList.get(i).equals(city)) {
                    graph.addEdge(cityList.get(i), city);
                    graph.setEdgeWeight(cityList.get(i), city,
                            GraphCalculationsHelper.distanceLatLngByCity(cityList.get(i), city));
                }

            }
        }

        System.out.println(count);

        return graph;
    }
}
