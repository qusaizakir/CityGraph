package main.OpenRouteAPI;

import retrofit2.Call;
import retrofit2.http.*;

public interface OpenRouteServiceApi {

    @Headers({
            "Accept: application/json"
    })
    @POST("v2/matrix/driving-car")
    Call<MatrixResponse> getMatrixByCar(@Body MatrixBody matrixBody, @Header("Authorization") String API_KEY);

    @Headers({
            "Accept: application/json"
    })
    @POST("v2/matrix/foot-walking")
    Call<MatrixResponse> getMatrixByFoot(@Body MatrixBody matrixBody, @Header("Authorization") String API_KEY);
}
