package main;

import com.opencsv.bean.MappingStrategy;

public class CountryCodeFilter{

	private final MappingStrategy strategy;

	public CountryCodeFilter(MappingStrategy strategy) {
		this.strategy = strategy;
	}

	public boolean allowLine(String[] line) {
		int index = strategy.getColumnIndex("STATE");
		String value = line[index];
		boolean result = !"production".equals(value);
		return result;
	}

}
