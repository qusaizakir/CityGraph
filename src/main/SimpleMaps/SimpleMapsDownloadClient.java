package main.SimpleMaps;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface SimpleMapsDownloadClient {

    @Streaming
    @GET
    Call<ResponseBody> getCountryCSVByCode(@Url String url);
}
