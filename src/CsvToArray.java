import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvToArray {

    private static final String PATH = "Resources/";
    private static final String EXT = ".csv";

    public static List<City> cityListByCountryName(String country){
        List<City> cityList;
        String path = PATH + country + EXT;
        try {
            Reader reader = Files.newBufferedReader(Paths.get("Resources/mali_minimal.csv"));
            CsvToBean<City> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(City.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            cityList = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cityList;
    }
}
