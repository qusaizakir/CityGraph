package main;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class GraphExportBean {

    private String label;
    @CsvBindByName(column = "From City")
    @CsvBindByPosition(position = 0)
    private String fromCity;
    @CsvBindByName(column = "To City")
    @CsvBindByPosition(position = 1)
    private String toCity;
    @CsvBindByName(column = "Driving Distance")
    @CsvBindByPosition(position = 2)
    private Double drivingDistance;
    @CsvBindByName(column = "Walking Distance")
    @CsvBindByPosition(position = 3)
    private Double walkingDistance;


    public GraphExportBean() {
    }

    public String getLabel() {
        return fromCity+toCity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Double getDrivingDistance() {
        return drivingDistance;
    }

    public void setDrivingDistance(Double drivingDistance) {
        this.drivingDistance = drivingDistance;
    }

    public Double getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(Double walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GraphExportBean) && ((GraphExportBean)obj).getLabel().equals(this.getLabel());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
