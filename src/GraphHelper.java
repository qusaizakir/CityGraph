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

        for(int i=0; i<cityList.size(); i++){
            for (City city : cityList) {
                if (!cityList.get(i).equals(city)) {
                    graph.addEdge(cityList.get(i), city);
                    graph.setEdgeWeight(cityList.get(i), city,
                            GraphCalculationsHelper.distanceLatLngByCity(cityList.get(i), city));
                }

            }
        }

        return graph;
    }

    public static SimpleWeightedGraph createWeightedGraphDrivingDistance(List<City> cityList, List<List<Double>> distances){
        SimpleWeightedGraph<City, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Add each city as a vertex
        //Add each edge, with straight line distance between cities as weight
        for(City c: cityList){
            graph.addVertex(c);
        }

        for(int i=0; i<cityList.size(); i++){
            for (int j=0; j<cityList.size(); j++) {
                if (!cityList.get(i).equals(cityList.get(j))) {
                    graph.addEdge(cityList.get(i), cityList.get(j));
                    if(distances.get(i).get(j) != null){
                        graph.setEdgeWeight(cityList.get(i), cityList.get(j),
                                distances.get(i).get(j));
                    }else{
                        graph.setEdgeWeight(cityList.get(i), cityList.get(j),
                                -1);
                    }


                }

            }
        }

        return graph;
    }
}
