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
		a.addBorder(c);



		ArrayList<Country> testList = new ArrayList<Country>();
		testList.add(a);
		testList.add(b);
		testList.add(c);

		System.out.println(a.canReach(testList));


	}

}
