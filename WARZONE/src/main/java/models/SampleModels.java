package models;

import java.util.ArrayList;

public class SampleModels {

	public static void main(String[] args) {

		Continent Africa = new Continent("Africa", 5);
		Continent Asia = new Continent("Asia", 10);
		Map cMap =  new Map();
		cMap.addContinent(Africa);
		cMap.addContinent(Asia);
		cMap.addCountry("Eygpt", Africa);
		cMap.addCountry("Sudan", Africa);
		cMap.addCountry("Yemen", Asia);
		cMap.addCountry("KSA", Asia);
		cMap.addBorder("Eygpt", "Sudan");
		cMap.addBorder("Sudan", "Eygpt");
		cMap.addBorder("Yemen","KSA");
		cMap.addBorder("KSA","Yemen");
		//cMap.addBorder("Sudan","Yemen");
		//cMap.addBorder("Yemen","Sudan");

		System.out.println(cMap.isConnected());
		System.out.println(cMap.isContinentConnected());
	}

}
