package main;

public class CSVCityLimit {

    private int population = 10000;
    private int cityLimit = 50;

    public CSVCityLimit() {
    }

    public CSVCityLimit(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getCityLimit() {
        return cityLimit;
    }

    public void setCityLimit(int cityLimit) {
        this.cityLimit = cityLimit;
    }
}
