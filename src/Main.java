import java.util.List;

public class Main {

    public static void main(String[] args){

        List<City> cityList = CsvToArray.cityListByCountryName("mali_minimal");
        for(City c: cityList){
            System.out.println(c.toString());
        }

    }
}
