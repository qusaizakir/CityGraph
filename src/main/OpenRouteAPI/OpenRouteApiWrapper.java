package main.OpenRouteAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class OpenRouteApiWrapper {

    public static final String API_KEY = "5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2";
    private Retrofit retrofit;
    private OpenRouteServiceApi routeApi;
    private OpenRouteAPICallbacks callbacks;

    public OpenRouteApiWrapper(OpenRouteAPICallbacks callbacks){
        this.callbacks = callbacks;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routeApi = retrofit.create(OpenRouteServiceApi.class);
    }

    public void getMatrixDistanceByCar(MatrixBody matrixBody){

        Call<MatrixResponse> call = routeApi.getMatrixByCar(matrixBody);

        call.enqueue(new Callback<MatrixResponse>() {
            @Override
            public void onResponse(Call<MatrixResponse> call, Response<MatrixResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("Response message: " + response.message());
                    System.out.println("Response code: " + response.code());
                    try {
                        String error = response.errorBody().string();
                        System.out.println(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    MatrixResponse matrixResponse = response.body();
                    callbacks.OnDriveMatrixSuccess(matrixResponse);
                }
            }

            @Override
            public void onFailure(Call<MatrixResponse> call, Throwable throwable) {
                System.out.println("Failure code: " + throwable.getMessage());
            }
        });
    }

    public void getMatrixDistanceByFoot(MatrixBody matrixBody){

        Call<MatrixResponse> call = routeApi.getMatrixByFoot(matrixBody);

        call.enqueue(new Callback<MatrixResponse>() {
            @Override
            public void onResponse(Call<MatrixResponse> call, Response<MatrixResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("Response message: " + response.message());
                    System.out.println("Response code: " + response.code());
                    try {
                        String error = response.errorBody().string();
                        System.out.println(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    MatrixResponse matrixResponse = response.body();
                    callbacks.OnFootMatrixSuccess(matrixResponse);
                }
            }

            @Override
            public void onFailure(Call<MatrixResponse> call, Throwable throwable) {
                System.out.println("Failure code: " + throwable.getMessage());
            }
        });

    }
}
