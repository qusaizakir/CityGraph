import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class OpenRouteApiWrapper {

    public static final String API_KEY = "5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2";
    private Retrofit retrofit;
    private OpenrouteServiceApi routeApi;
    private OpenRouteAPICallbacks callbacks;

    public OpenRouteApiWrapper(OpenRouteAPICallbacks callbacks){
        this.callbacks = callbacks;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routeApi = retrofit.create(OpenrouteServiceApi.class);
    }

    public void getDistanceByCar(City fromCity, City toCity){

        Call<Directions> call = routeApi.getRouteByCar(API_KEY, fromCity.getCoordinatesString(), toCity.getCoordinatesString());

        call.enqueue(new Callback<Directions>() {
            @Override
            public void onResponse(Call<Directions> call, Response<Directions> response) {
                if(!response.isSuccessful()){
                    System.out.println("Response code: " + response.message());
                }else{
                    Summary summary = response.body().getFeatures().get(0).getProperties().getSummary();
                    summary.setFromCity(fromCity);
                    summary.setToCity(toCity);
                    callbacks.OnSummarySuccess(summary);
                }
            }

            @Override
            public void onFailure(Call<Directions> call, Throwable throwable) {
                System.out.println("Failure code: " + throwable.getMessage());
            }


        });
    }

    public void getMatrixDistanceByCar(GPSCityLocations gpsCityLocations){

        Call<Matrix> call = routeApi.getMatrixByCar(gpsCityLocations);

        call.enqueue(new Callback<Matrix>() {
            @Override
            public void onResponse(Call<Matrix> call, Response<Matrix> response) {
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
                    Matrix matrix = response.body();
                    callbacks.OnMatrixSuccess(matrix);
                }
            }

            @Override
            public void onFailure(Call<Matrix> call, Throwable throwable) {
                System.out.println("Failure code: " + throwable.getMessage());
            }
        });
    }
}
