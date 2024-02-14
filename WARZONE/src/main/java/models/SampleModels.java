package main.java.models;

import main.java.models.map.Border;
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

		ExampleMap.addContinent(1,"Africa"); //Add Continent objects
		ExampleMap.addContinent(2,"Asia");


		ExampleMap.addCountry(1,1, "Egypt"); //Add Country objects
		ExampleMap.addCountry(2,1, "Sudan");
		ExampleMap.addCountry(3,2, "Yemen");
		ExampleMap.addCountry(4,2, "KSA");


		ExampleMap.addBorder(1,2); //Add Border objects
		ExampleMap.addBorder(2,1);
		ExampleMap.addBorder(3,4);
		ExampleMap.addBorder(4,3);
		ExampleMap.addBorder(2,3);
		ExampleMap.addBorder(3,2);

		System.out.println(ExampleMap.isConnected()); //check if Map is connected graph

		System.out.println(ExampleMap.isContinentConnected()); //check if Continents are connected sub-graphs

	}

}
