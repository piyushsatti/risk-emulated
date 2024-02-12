package models;

import java.util.ArrayList;

public class SampleModels {

	public static void main(String[] args) {

		Map testMap = new Map();
		Country a = new Country("a",1);
		Country b = new Country("b",1);
		Country c = new Country("c",1);
		Country d = new Country("d", 1);
		Country e = new Country("e", 1);

		a.addBorder(b);
		b.addBorder(c);
		c.addBorder(d);
		d.addBorder(e);
		e.addBorder(a);

		ArrayList<Country> testList = new ArrayList<Country>();
		testList.add(a);
		testList.add(b);
		testList.add(c);

		testMap.addCountry(a);
		testMap.addCountry(b);
		testMap.addCountry(c);
		testMap.addCountry(d);
		testMap.addCountry(e);

		System.out.println(testMap.isConnected());

		System.out.println(a.canReach(testList));
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
