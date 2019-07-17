package main.OpenRouteAPI;

import java.util.List;

public class MatrixBody {

	private List<List<Double>> locations;
	private String[] metrics;
	private String units;
	public static final String[] DISTANCE = {"distance"};
	public static final String KM = "km";

	public MatrixBody(List<List<Double>> locations, String[] metrics, String units) {
		this.locations = locations;
		this.metrics = metrics;
		this.units = units;
	}

	public List<List<Double>> getLocations() {
		return locations;
	}

	public void setLocations(List<List<Double>> locations) {
		this.locations = locations;
	}

	public String[] getMetrics() {
		return metrics;
	}

	public void setMetrics(String[] metrics) {
		this.metrics = metrics;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
}
