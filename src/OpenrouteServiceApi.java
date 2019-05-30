import retrofit2.Call;
import retrofit2.http.*;

public interface OpenrouteServiceApi {

    @GET("v2/directions/driving-car")
    Call<Directions> getRouteByCar(@Query("api_key")String apiKey,
                                   @Query("start") String fromCity,
                                   @Query("end") String toCity);

    @Headers({
            "Authorization: 5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2",
            "Accept: application/json"
    })
    @POST("v2/matrix/driving-car")
    Call<Matrix> getMatrixByCar(@Body GPSCityLocations gpsCityLocations);
}
