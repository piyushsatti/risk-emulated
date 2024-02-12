package models;

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

		testMap.addCountry(a);
		testMap.addCountry(b);
		testMap.addCountry(c);
		testMap.addCountry(d);
		testMap.addCountry(e);

		System.out.print(a);
	}

}
