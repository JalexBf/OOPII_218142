package alex;

import java.util.List;

public class App {
	private static void printCities(PerceptronTraveller p) {
		List<City> recomendedList= p.recommend(false);
		for(City city:recomendedList) {
			System.out.format("[%s, %s]\n", city.getName(), city.getCountry());
		}
		System.out.println("==================================");
	}
	
	
	public static void main(String[] args) {
		PerceptronTraveller p;
		p= new PerceptronYoungTraveller();		printCities(p);
		p= new PerceptronMiddleTraveller();		printCities(p);
		p= new PerceptronElderTraveller();		printCities(p);
	}

}
