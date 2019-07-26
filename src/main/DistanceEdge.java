package main;

import org.jgrapht.graph.DefaultWeightedEdge;

public class DistanceEdge extends DefaultWeightedEdge {

	private String label;
	static String DRIVING_CAR = "Driving_Car";
	static String FOOT = "Foot";
	static String STRAIGHT = "Straight";

	public DistanceEdge(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getSourceCityName() {
		return ((City) getSource()).getName();
	}

	public String getTargetCityName() {
		return ((City) getTarget()).getName();
	}

	@Override
	protected double getWeight() {
		return super.getWeight();
	}

	@Override
	public String toString() {
		return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
	}
}
