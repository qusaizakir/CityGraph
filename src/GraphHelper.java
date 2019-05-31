import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class GraphHelper {

    public static SimpleWeightedGraph createWeightedGraphStraightLineDistance(List<City> cityList){
        SimpleWeightedGraph<City, DistanceEdge> graph = new SimpleWeightedGraph<>(DistanceEdge.class);

        //Add each city as a vertex
        //Add each edge, with straight line distance between cities as weight
        for(City c: cityList){
            graph.addVertex(c);
        }

        for(int i=0; i<cityList.size(); i++){
            for (City city : cityList) {
                if (!cityList.get(i).equals(city)) {
                    graph.addEdge(cityList.get(i), city, new DistanceEdge(DistanceEdge.STRAIGHT));
                    graph.setEdgeWeight(cityList.get(i), city,
                            GraphCalculationsHelper.distanceLatLngByCity(cityList.get(i), city));
                }

            }
        }

        return graph;
    }

    public static SimpleWeightedGraph createWeightedGraphDrivingDistance(List<City> cityList, List<List<Double>> distances){
        SimpleWeightedGraph<City, DistanceEdge> graph = new SimpleWeightedGraph<>(DistanceEdge.class);

        //Add each city as a vertex
        for(City c: cityList){
            graph.addVertex(c);
        }

        //Check to see if two cities are not the same
        //Add an edge between the cities with the driving distance as the weight
        for(int i=0; i<cityList.size(); i++){
            for (int j=0; j<cityList.size(); j++) {
                if (!cityList.get(i).equals(cityList.get(j))) {
                    graph.addEdge(cityList.get(i), cityList.get(j), new DistanceEdge(DistanceEdge.DRIVING_CAR));
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

    public static WeightedMultigraph createWeightedGraphDrivingAndFootDistance(List<City> cityList, List<List<Double>> driveDistance, List<List<Double>> footDistance){
        WeightedMultigraph<City, DistanceEdge> graph = new WeightedMultigraph<>(DistanceEdge.class);

        //Add each city as a vertex
        for(City c: cityList){
            graph.addVertex(c);
        }

        for(int i=0; i<cityList.size(); i++){
            for (int j=0; j<cityList.size(); j++) {
                if (!cityList.get(i).equals(cityList.get(j))) {

                    DistanceEdge eDrive = new DistanceEdge(DistanceEdge.DRIVING_CAR);
                    graph.addEdge(cityList.get(i), cityList.get(j), eDrive);
                    DistanceEdge eFoot = new DistanceEdge(DistanceEdge.FOOT);
                    graph.addEdge(cityList.get(i), cityList.get(j), eFoot);

                    //Add two sets of edges, one for driving and one for walking
                    if(driveDistance.get(i).get(j) != null){
                        graph.setEdgeWeight(eDrive, driveDistance.get(i).get(j));
                    }else{
                        graph.setEdgeWeight(eDrive, -1);
                    }

                    if(footDistance.get(i).get(j) != null){
                        graph.setEdgeWeight(eFoot, footDistance.get(i).get(j));
                    }else{
                        graph.setEdgeWeight(eFoot, -1);
                    }
                }
            }
        }

        return graph;
    }
}
