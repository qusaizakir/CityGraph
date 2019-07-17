package main.OpenRouteAPI;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OpenRouteApiWrapper {

	private String API_KEY;
	private Retrofit retrofit;
	private OpenRouteServiceApi routeApi;
	private OpenRouteAPICallbacks callbacks;

	public OpenRouteApiWrapper(String API_KEY) {
		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.connectTimeout(25, TimeUnit.SECONDS);
		client.readTimeout(25, TimeUnit.SECONDS);
		client.writeTimeout(25, TimeUnit.SECONDS);
		client.callTimeout(25, TimeUnit.SECONDS);

		retrofit = new Retrofit.Builder()
				.baseUrl("https://api.openrouteservice.org/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(client.build())
				.build();
		routeApi = retrofit.create(OpenRouteServiceApi.class);
		this.API_KEY = API_KEY;

	}

	public MatrixResponse getMatrixDistanceByCar(MatrixBody matrixBody) {

		Call<MatrixResponse> call = routeApi.getMatrixByCar(matrixBody, API_KEY);
		MatrixResponse matrixResponse = null;
		Response response = null;
		try {
			response = call.execute();
			if (response.isSuccessful()) {
				matrixResponse = (MatrixResponse) response.body();
			} else {
				System.out.println("Error: " + response.errorBody().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return matrixResponse;
	}

	public MatrixResponse getMatrixDistanceByFoot(MatrixBody matrixBody) {

		Call<MatrixResponse> call = routeApi.getMatrixByFoot(matrixBody, API_KEY);
		MatrixResponse matrixResponse = null;
		Response response;
		try {
			response = call.execute();
			if (response.isSuccessful()) {
				matrixResponse = (MatrixResponse) response.body();
			} else {
				System.out.println("Error: " + response.errorBody().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return matrixResponse;

	}
}
