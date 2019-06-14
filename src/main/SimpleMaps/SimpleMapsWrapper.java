package main.SimpleMaps;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleMapsWrapper {
    private SimpleMapsDownloadClient downloadClient;
    private final String SIMPLE_MAPS_URL = "https://simplemaps.com/static/data/country-cities"; // "/gb/gb.csv"
    private final String CSV = ".csv";

    public SimpleMapsWrapper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://simplemaps.com/")
                .build();
        downloadClient = retrofit.create(SimpleMapsDownloadClient.class);
    }

    public void downloadCountryCSVByCode(String countryCode){

        String name = countryCode + CSV;
        String url = SIMPLE_MAPS_URL + "/" + countryCode + "/" + countryCode + CSV;
        System.out.println("SimpleMaps URL for " + countryCode + " :" + url);

        Call<ResponseBody> call = downloadClient.getCountryCSVByCode(url);
        try {
            ResponseBody responseBody = call.execute().body();
            if(responseBody != null){
                writeResponseBodyToDisk(responseBody, name);
                System.out.println("File downloaded");
            }else{
                System.out.println("Error: Incorrect country code");
                System.exit(9);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {

        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();

        try {
            File file = new File(path + File.separator + fileName);
            System.out.println("Location of files: " + path + File.separator + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    System.out.println( "File Progress: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return true;

            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
