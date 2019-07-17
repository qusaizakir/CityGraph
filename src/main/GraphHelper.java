package main;

import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class GraphHelper {

	public static SimpleWeightedGraph createWeightedGraphStraightLineDistance(List<City> cityList) {
		SimpleWeightedGraph<City, DistanceEdge> graph = new SimpleWeightedGraph<>(DistanceEdge.class);

		//Add each city as a vertex
		//Add each edge, with straight line distance between cities as weight
		for (City c : cityList) {
			graph.addVertex(c);
		}

		for (int i = 0; i < cityList.size(); i++) {
			for (City city : cityList) {
				if (!cityList.get(i).equals(city)) {
					graph.addEdge(cityList.get(i), city, new DistanceEdge(DistanceEdge.STRAIGHT));
					graph.setEdgeWeight(cityList.get(i), city,
							DistanceMetricHelper.distanceLatLngByCity(cityList.get(i), city));
				}

			}
		}

		return graph;
	}

	public static WeightedMultigraph createWeightedGraphDrivingAndWalkDistance(List<City> cityList, List<List<Double>> driveDistance, List<List<Double>> footDistance) {
		WeightedMultigraph<City, DistanceEdge> graph = new WeightedMultigraph<>(DistanceEdge.class);

		//Add each city as a vertex
		for (City c : cityList) {
			graph.addVertex(c);
		}

		//If the cities are the same, do not add an edge (self loop)
		//If the cities already have an edge (A-B == B-A), do not add an edge
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {

				if (!cityList.get(i).equals(cityList.get(j))) {
					if (graph.getEdge(cityList.get(i), cityList.get(j)) == null) {

						DistanceEdge eDrive = new DistanceEdge(DistanceEdge.DRIVING_CAR);
						graph.addEdge(cityList.get(i), cityList.get(j), eDrive);
						DistanceEdge eFoot = new DistanceEdge(DistanceEdge.FOOT);
						graph.addEdge(cityList.get(i), cityList.get(j), eFoot);

						//Add two sets of edges, one for driving and one for walking
						if (driveDistance.get(i).get(j) != null) {
							graph.setEdgeWeight(eDrive, driveDistance.get(i).get(j));
						} else {
							graph.setEdgeWeight(eDrive, -1);
						}

						if (footDistance.get(i).get(j) != null) {
							graph.setEdgeWeight(eFoot, footDistance.get(i).get(j));
						} else {
							graph.setEdgeWeight(eFoot, -1);
						}
					}
				}
			}
		}

		return graph;
	}

	public static WeightedMultigraph createWeightedGraphDrivingDistance(List<City> cityList, List<List<Double>> driveDistance) {
		WeightedMultigraph<City, DistanceEdge> graph = new WeightedMultigraph<>(DistanceEdge.class);

		//Add each city as a vertex
		for (City c : cityList) {
			graph.addVertex(c);
		}

		//If the cities are the same, do not add an edge (self loop)
		//If the cities already have an edge (A-B == B-A), do not add an edge
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {

				if (!cityList.get(i).equals(cityList.get(j))) {
					if (graph.getEdge(cityList.get(i), cityList.get(j)) == null) {

						DistanceEdge eDrive = new DistanceEdge(DistanceEdge.DRIVING_CAR);
						graph.addEdge(cityList.get(i), cityList.get(j), eDrive);

						//Add one edge for driving only
						if (driveDistance.get(i).get(j) != null) {
							graph.setEdgeWeight(eDrive, driveDistance.get(i).get(j));
						} else {
							graph.setEdgeWeight(eDrive, -1);
						}

					}
				}
			}
		}

		return graph;
	}

	public static WeightedMultigraph createWeightedGraphWalkingDistance(List<City> cityList, List<List<Double>> driveDistance) {
		WeightedMultigraph<City, DistanceEdge> graph = new WeightedMultigraph<>(DistanceEdge.class);

		//Add each city as a vertex
		for (City c : cityList) {
			graph.addVertex(c);
		}

		//If the cities are the same, do not add an edge (self loop)
		//If the cities already have an edge (A-B == B-A), do not add an edge
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {

				if (!cityList.get(i).equals(cityList.get(j))) {
					if (graph.getEdge(cityList.get(i), cityList.get(j)) == null) {

						DistanceEdge eWalk = new DistanceEdge(DistanceEdge.FOOT);
						graph.addEdge(cityList.get(i), cityList.get(j), eWalk);

						//Add one edge for walking only
						if (driveDistance.get(i).get(j) != null) {
							graph.setEdgeWeight(eWalk, driveDistance.get(i).get(j));
						} else {
							graph.setEdgeWeight(eWalk, -1);
						}

					}
				}
			}
		}

		return graph;
	}

}
