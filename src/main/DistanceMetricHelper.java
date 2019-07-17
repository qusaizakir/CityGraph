package main;

public class DistanceMetricHelper {

	//returns straightline distance in KM rounded to integer
	private static double distanceLatLng(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c;

		return Math.round(distance);
	}

	public static double distanceLatLngByCity(City city1, City city2) {
		return distanceLatLng(city1.getLat(), city2.getLat(), city1.getLng(), city2.getLng());
	}
}
