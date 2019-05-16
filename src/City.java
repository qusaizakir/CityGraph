import com.opencsv.bean.CsvBindByName;

public class City {

    @CsvBindByName
    private String city;

    @CsvBindByName
    private double lat;

    @CsvBindByName
    private double lng;

    @CsvBindByName
    private String country;

    @CsvBindByName
    private int population;

    public City() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
        return country + "--" + city + "--" + lat + "--" + lng + "--" + population;
    }
}