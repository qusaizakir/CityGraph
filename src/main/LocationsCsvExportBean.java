package main;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class LocationsCsvExportBean {

	private String label;

	@CsvBindByName(column = "name")
	@CsvBindByPosition(position = 0)
	private String city;

	@CsvBindByName(column = "region")
	@CsvBindByPosition(position = 1)
	private String region;

	@CsvBindByName(column = "country")
	@CsvBindByPosition(position = 2)
	private String country;

	@CsvBindByName(column = "lat")
	@CsvBindByPosition(position = 3)
	private Double lat;

	@CsvBindByName(column = "lon")
	@CsvBindByPosition(position = 4)
	private Double lng;

	@CsvBindByName(column = "location_type")
	@CsvBindByPosition(position = 5)
	private String locationType;

	@CsvBindByName(column = "conflict_date")
	@CsvBindByPosition(position = 6)
	private Integer conflictDate;

	@CsvBindByName(column = "population")
	@CsvBindByPosition(position = 7)
	private Integer population;

	@CsvBindByName
	private String iso2;


	public LocationsCsvExportBean() {
		this.region = "";
	}

	public String getRegion() {
		return region;
	}

	public String getLabel() {
		return lat + lng + "";
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
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

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof RoutesGraphCsvExportBean) && ((RoutesGraphCsvExportBean) obj).getLabel().equals(this.getLabel());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}