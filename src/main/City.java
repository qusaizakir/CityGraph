package main;
import com.opencsv.bean.CsvBindByName;

public class City {

	@CsvBindByName
	private String name;

	@CsvBindByName
	private double lat;

	@CsvBindByName
	private double lon;

	@CsvBindByName(column = "location_type")
	private String locationType;

	@CsvBindByName(column = "conflict_date")
	private Integer conflictDate;

	@CsvBindByName
	private String country;

	@CsvBindByName
	private String iso2;

	@CsvBindByName
	private int population;

	public City() {
		this.locationType = "town";
		this.conflictDate = 0;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Integer getConflictDate() {
		return conflictDate;
	}

	public void setConflictDate(Integer conflictDate) {
		this.conflictDate = conflictDate;
	}

	public String getName() {
		return name;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
	public String toString() {
		return name + ", " + lat + ", " + lon + ", " + locationType + ", " + conflictDate + ", " + country + ", " + iso2 + ", " + population;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof City)){
			return false;
		}
		City other = (City) obj;
		if(other.getName().equalsIgnoreCase(this.getName())){
			return true;
		}else return DistanceMetricHelper.distanceLatLngByCity(other, this) <= 10;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}