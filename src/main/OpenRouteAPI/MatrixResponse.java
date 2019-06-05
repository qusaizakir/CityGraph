package main.OpenRouteAPI;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatrixResponse {

    @SerializedName("distances")
    @Expose
    private List<List<Double>> distances = null;

    public List<List<Double>> getDistances() {
        return distances;
    }

    public void setDistances(List<List<Double>> distances) {
        this.distances = distances;
    }
}



