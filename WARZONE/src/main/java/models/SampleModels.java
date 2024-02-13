package main.java.models;

import main.java.models.map.Continent;
import main.java.models.map.Country;
import main.java.models.map.Map;

import java.util.ArrayList;

public class SampleModels {

	public static void main(String[] args) {

		Map cMap =  new Map();
		cMap.addContinent("Africa");
		cMap.addContinent("Asia");
		cMap.addCountry("Egypt", "Africa");
		cMap.addCountry("Sudan", "Africa");
		cMap.addCountry("Yemen", "Asia");
		cMap.addCountry("KSA", "Asia");
		cMap.addBorder("Egypt", "Sudan");
		cMap.addBorder("Sudan", "Egypt");
		cMap.addBorder("Yemen","KSA");
		cMap.addBorder("KSA","Yemen");
		cMap.addBorder("Sudan","Yemen");
		cMap.addBorder("Yemen","Sudan");

		System.out.println(cMap.isConnected());
		System.out.println(cMap.isContinentConnected());
	}

}
