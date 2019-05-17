import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenRouteApiWrapper {

    public static final String API_KEY = "5b3ce3597851110001cf6248a8ff237b2fc749d59c870996c11e02f2";
    private Retrofit retrofit;
    private OpenrouteServiceApi routeApi;
    private SummaryCallbacks callbacks;

    public OpenRouteApiWrapper(SummaryCallbacks callbacks){
        this.callbacks = callbacks;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routeApi = retrofit.create(OpenrouteServiceApi.class);
    }

    public void getDistanceByCar(City fromCity, City toCity){

        Call<Directions> call = routeApi.getRoute(API_KEY, fromCity.getCoordinatesString(), toCity.getCoordinatesString());

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
}
