package main.OpenRouteAPI;

import retrofit2.Call;
import retrofit2.http.*;

public interface OpenRouteServiceApi {

    @Headers({
            "Authorization: 5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2",
            "Accept: application/json"
    })
    @POST("v2/matrix/driving-car")
    Call<MatrixResponse> getMatrixByCar(@Body MatrixBody matrixBody);

    @Headers({
            "Authorization: 5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2",
            "Accept: application/json"
    })
    @POST("v2/matrix/foot-walking")
    Call<MatrixResponse> getMatrixByFoot(@Body MatrixBody matrixBody);
}
