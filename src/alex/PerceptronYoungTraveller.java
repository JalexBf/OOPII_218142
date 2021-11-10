package alex;

import java.util.ArrayList;
import java.util.List;

public class PerceptronYoungTraveller implements PerceptronTraveller{
	double weights[]= {0.8, 1, 0.5, -0.5, 1, -1, 1, 0, 0, 1};
	
	public List<City> recommend() {
		List<City> recommendedCities= new ArrayList<>();
		Cities cities= Cities.getInstance();
		for(City city: cities.getCities()) {
			double sum=0.0;
			double terms[]= city.getTerms();
			for(int i=0; i<10; i++) {
				sum += terms[i]*weights[i];
			}
			if(sum>=0) {
				recommendedCities.add(city);
			}
		}
		return recommendedCities;
	}
	
	public List<City> recommend(boolean flag) {
		List<City> recommendedCities= new ArrayList<>();
		Cities cities= Cities.getInstance();
		for(City city: cities.getCities()) {
			double sum=0.0;
			double terms[]= city.getTerms();
			for(int i=0; i<10; i++) {
				sum += terms[i]*weights[i];
			}
			if(sum>=0) {
				if(flag) {
					city.setName(city.getName().toUpperCase());
				}else {
					city.setName(city.getName().toLowerCase());
				}
				recommendedCities.add(city);
			}
		}
		return recommendedCities;
	}
	
}
