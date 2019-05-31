import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.io.CSVExporter;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.ComponentNameProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.jgrapht.io.CSVFormat.Parameter.EDGE_WEIGHTS;

public class CSVHelper {

    private static final String PATH = "Resources/";
    private static final String EXT = ".csv";
    private static ComponentNameProvider<City> vertexIdProvider = city -> city.getCity();
    private static ComponentNameProvider<DistanceEdge> edgeIdProvider = distanceEdge -> distanceEdge.getLabel();

    public static List<City> cityListByCountryName(String country){
        List<City> cityList;
        String path = PATH + country + EXT;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
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

    public static void exportGraphToCSV(SimpleWeightedGraph graph, String fileName){
        String path = PATH + fileName + EXT;

        CSVExporter<City, DistanceEdge> exporter = new CSVExporter(CSVFormat.EDGE_LIST);
        exporter.setVertexIDProvider(vertexIdProvider);
        exporter.setParameter(EDGE_WEIGHTS, true);

        Writer writer = null;
        try {
            writer = new FileWriter(path);
            exporter.exportGraph(graph, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportGraphToCSV(WeightedMultigraph graph, String fileName){
        String path = PATH + fileName + EXT;

        CSVExporter<City, DistanceEdge> exporter = new CSVExporter(CSVFormat.EDGE_LIST);
        exporter.setEdgeIDProvider(edgeIdProvider);
        exporter.setVertexIDProvider(vertexIdProvider);
        exporter.setParameter(EDGE_WEIGHTS, true);

        Writer writer = null;
        try {
            writer = new FileWriter(path);
            exporter.exportGraph(graph, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
