package main.java.models;

import main.java.models.map.Continent;
import main.java.models.map.Country;
import main.java.models.map.Map;

public class SampleModels {

	public static void main(String[] args) {

		/*
			Maps contain Continents and Countries in the HashMap
			Countries contain a HashMap of Border objects and a Continent reference
		 */


		Map ExampleMap =  new Map(); //create a map

		ExampleMap.addContinent("Africa"); //Add Continent objects
		ExampleMap.addContinent("Asia");


		ExampleMap.addCountry("Egypt", "Africa"); //Add Country objects
		ExampleMap.addCountry("Sudan", "Africa");
		ExampleMap.addCountry("Yemen", "Asia");
		ExampleMap.addCountry("KSA", "Asia");


		ExampleMap.addBorder("Egypt", "Sudan"); //Add Border objects
		ExampleMap.addBorder("Sudan", "Egypt");
		ExampleMap.addBorder("Yemen","KSA");
		ExampleMap.addBorder("KSA","Yemen");
		ExampleMap.addBorder("Sudan","Yemen");
		ExampleMap.addBorder("Yemen","Sudan");

		System.out.println(ExampleMap.isConnected()); //check if Map is connected graph

		System.out.println(ExampleMap.isContinentConnected()); //check if Continents are connected sub-graphs
	}

}
