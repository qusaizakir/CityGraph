import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenrouteServiceApi {

    @GET("v2/directions/driving-car")
    Call<Directions> getRoute(@Query("api_key")String apiKey,
                         @Query("start") String fromCity,
                         @Query("end") String toCity);
}
