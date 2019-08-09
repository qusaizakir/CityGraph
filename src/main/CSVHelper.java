package main;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.io.CSVExporter;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.ComponentNameProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.jgrapht.io.CSVFormat.Parameter.EDGE_WEIGHTS;

public class CSVHelper {

	private static final String PATH = Paths.get("").toAbsolutePath().toString();
	private static final String EXT = ".csv";
	private static ComponentNameProvider<City> vertexIdProvider = city -> city.getName();

	public static List<City> cityListByCountryName(String country, CSVCityLimit cityLimit) {
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

		System.out.println("The number of cities before filtering: " + cityList.size());
		List<City> citiesEdited = removeCitiesBasedOnFilter(cityList, cityLimit);
		System.out.println("The number of cities after filtering: " + citiesEdited.size());
		if (citiesEdited.size() < 2) {
			System.out.println("Error: The number of cities is below 2. Lower the population limit and/or cities limit.");
			System.exit(9);
		}
		return citiesEdited;

	}

	public static List<City> cityListByCountryNameOffline(String country, CSVCityLimit cityLimit) {

		List<City> cityList;
		String path = PATH + File.separator + "worldcities" + EXT;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.ISO_8859_1);
			CsvToBean<City> csvToBean = new CsvToBeanBuilder(reader)
					.withType(City.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();

			cityList = csvToBean.parse();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		System.out.println("The number of cities before filtering: " + cityList.size());
		removeCitiesNotInCountry(cityList, country);
		removeCitiesBasedOnFilter(cityList, cityLimit);
		System.out.println("The number of cities after filtering: " + cityList.size());
		if (cityList.size() < 2) {
			System.out.println("Error: The number of cities is below 2. Lower the population limit and/or cities limit.");
			System.exit(9);
		}
		return cityList;
	}

	public static List<City> cityListByLocationsInput(String country){


		List<City> cityList;
		String path = PATH + File.separator + "locations" + EXT;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(path));
			CsvToBean<City> csvToBean = new CsvToBeanBuilder(reader)
					.withType(City.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();

			cityList = csvToBean.parse();
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}

		for (City c: cityList){
			c.setIso2(country);
		}

		return cityList;
	}

	private static List<City> removeCitiesNotInCountry(List<City> cityList, String country) {
		cityList.removeIf(c -> !c.getIso2().toLowerCase().equals(country.toLowerCase()));
		return cityList;
	}

	public static void exportGraphToDefaultCSVFormat(SimpleWeightedGraph graph, String fileName) {
		String path = PATH + File.separator + fileName + EXT;

		CSVExporter exporter = new CSVExporter(CSVFormat.EDGE_LIST);
		exporter.setVertexIDProvider(vertexIdProvider);
		exporter.setParameter(EDGE_WEIGHTS, true);

		Writer writer = null;
		try {
			writer = new FileWriter(path);
			exporter.exportGraph(graph, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void exportGraphToCustomCSVFormat(WeightedMultigraph graph, String fileName) {
		String path = PATH + File.separator + fileName + EXT;

		ArrayList<RoutesGraphCsvExportBean> beans = formatRoutesGraphForExport(graph);

		CustomMappingStrategy strategy = new CustomMappingStrategy();
		strategy.setType(RoutesGraphCsvExportBean.class);


		Writer writer = null;
		try {
			writer = new FileWriter(path);
			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
			beanToCsv.write(beans);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void exportLocationGraphToCustomCSVFormat(List<City> cityList, String fileName) {
		String path = PATH + File.separator + fileName + EXT;

		ArrayList<LocationsCsvExportBean> beans = formatLocationsGraphForExport(cityList);

		CustomMappingStrategy strategy = new CustomMappingStrategy();
		strategy.setType(LocationsCsvExportBean.class);


		Writer writer = null;
		try {
			writer = new FileWriter(path);
			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
			beanToCsv.write(beans);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<LocationsCsvExportBean> formatLocationsGraphForExport(List<City> cityList) {
		ArrayList<LocationsCsvExportBean> exportBeans = new ArrayList<>();

		for (City c : cityList) {
			LocationsCsvExportBean bean = new LocationsCsvExportBean();
			bean.setCity(c.getName());
			bean.setCountry(c.getCountry());
			bean.setLat(c.getLat());
			bean.setLng(c.getLon());
			bean.setPopulation(c.getPopulation());
			bean.setIso2(c.getIso2());

			bean.setConflictDate(c.getConflictDate());
			bean.setLocationType(c.getLocationType());

			exportBeans.add(bean);
		}

		return exportBeans;
	}

	private static ArrayList<RoutesGraphCsvExportBean> formatRoutesGraphForExport(WeightedMultigraph graph) {
		ArrayList<RoutesGraphCsvExportBean> exportBeans = new ArrayList<>();

		//Get edges from graph and convert to list
		List<DistanceEdge> edges = new ArrayList<>();
		edges.addAll(graph.edgeSet());

		//Use Graph Export Bean as a template for exporting in correct format
		for (DistanceEdge e : edges) {
			RoutesGraphCsvExportBean bean = new RoutesGraphCsvExportBean();
			bean.setFromCity(e.getSourceCityName());
			bean.setToCity(e.getTargetCityName());
			if (exportBeans.contains(bean)) {
				bean = exportBeans.get(exportBeans.indexOf(bean));
			}

			if (e.getLabel().equals(DistanceEdge.DRIVING_CAR)) {
				bean.setDrivingDistance(e.getWeight());
			} else {
				bean.setWalkingDistance(e.getWeight());
			}
			if (!exportBeans.contains(bean)) {
				exportBeans.add(bean);
			}
		}
		return exportBeans;
	}

	private static List<City> removeCitiesBasedOnFilter(List<City> cityList, CSVCityLimit cityLimit) {
		int pop = cityLimit.getPopulation();
		int limit = cityLimit.getCityLimit();

		//Remove cities below pop level
		cityList.removeIf(c -> c.getPopulation() < pop && c.getPopulation() != 0);

		//If cities over limit, then add only limited number to new list (highest pop first)
		if (cityList.size() > limit) {
			List<City> newList = new ArrayList<>();
			for (int i = 0; i < limit; i++) {
				newList.add(cityList.get(i));
			}
			cityList.clear();
			cityList.addAll(newList);
			return cityList;
		} else {
			return cityList;
		}

	}
}
