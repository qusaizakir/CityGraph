package main;

import com.opencsv.bean.*;
import com.opencsv.bean.comparator.LiteralComparator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.io.CSVExporter;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.ComponentNameProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.jgrapht.io.CSVFormat.Parameter.EDGE_WEIGHTS;

public class CSVHelper {

    private static final String PATH = Paths.get("").toAbsolutePath().toString();
    private static final String EXT = ".csv";
    private static ComponentNameProvider<City> vertexIdProvider = city -> city.getCity();
    private static ComponentNameProvider<DistanceEdge> edgeIdProvider = distanceEdge -> distanceEdge.getLabel();

    public static List<City> cityListByCountryName(String country, CSVCityLimit cityLimit){
        List<City> cityList;
        String path = PATH + File.separator + country + EXT;
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
        return removeCitiesBasedOnFilter(cityList, cityLimit);
    }

    public static void exportGraphToDefaultCSVFormat(SimpleWeightedGraph graph, String fileName){
        String path = PATH + File.separator + fileName + EXT;

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

    public static void exportGraphToDefaultCSVFormat(WeightedMultigraph graph, String fileName){
        String path =  PATH + File.separator + fileName + EXT;

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

    public static void exportGraphToCustomCSVFormat(WeightedMultigraph graph, String fileName){
        String path = PATH + File.separator + fileName + EXT;

        ArrayList<GraphExportBean> beans = formatGraphForExport(graph);

        CustomMappingStrategy strategy = new CustomMappingStrategy();
        strategy.setType(GraphExportBean.class);


        Writer writer = null;
        try {
            writer = new FileWriter(path);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
            beanToCsv.write(beans);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList<GraphExportBean> formatGraphForExport(WeightedMultigraph graph){
        ArrayList<GraphExportBean> exportBeans = new ArrayList<>();

        //Get edges from graph and convert to list
        List<DistanceEdge> edges = new ArrayList<>();
        edges.addAll(graph.edgeSet());

        //Use Graph Export Bean as a template for exporting in correct format
        for(DistanceEdge e: edges){
            GraphExportBean bean = new GraphExportBean();
            bean.setFromCity(e.getSourceCityName());
            bean.setToCity(e.getTargetCityName());
            if(exportBeans.contains(bean)){
               bean = exportBeans.get(exportBeans.indexOf(bean));
            }

            if(e.getLabel().equals(DistanceEdge.DRIVING_CAR)){
                bean.setDrivingDistance(e.getWeight());
            }else{
                bean.setWalkingDistance(e.getWeight());
            }
            if(!exportBeans.contains(bean)){
                exportBeans.add(bean);
            }
        }

        return exportBeans;
    }

    private static List<City> removeCitiesBasedOnFilter(List<City> cityList, CSVCityLimit cityLimit){
        int pop = cityLimit.getPopulation();
        int limit = cityLimit.getCityLimit();

        //Remove cities below pop level
        cityList.removeIf(c -> c.getPopulation() < pop);

        //If cities over limit, then add only limited number to new list (highest pop first)
        if(cityList.size() > limit){
            List<City> newList = new ArrayList<>();
            for(int i=0; i<limit; i++){
                newList.add(cityList.get(i));
            }
            return newList;
        }else{
            return cityList;
        }

    }
}