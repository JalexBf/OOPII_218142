package alex;

import java.util.ArrayList;
import java.util.List;
import opendata.OpenData;
import geodesic.DistanceCalculator;

public class Cities {
	final static int TEMP_MAX= 331;
	final static int TEMP_MIN= 184;
	final static int TERM_MAX= 10;
	final static int TERM_MIN= 0;
	
	private static Cities instance= null;
	
	public static Cities getInstance() {
		if(instance==null) instance= new Cities();
		return instance;
	}
	
	private List<City> cities= new ArrayList<>();
	
	public List<City> getCities() {
		return cities;
	}
	
	// Normalization
	public double scale(double t, double min, double max){
		if(t<min) t= min;
		if(t>max) t=max;
		return (t-min)/(max-min);
	}
	
	// Hardcoded
	private Cities() {
		cities.add(new City("Athens", "GR"));
		cities.add(new City("Rome", "IT"));
		cities.add(new City("London", "UK"));
		cities.add(new City("Thessaloniki", "GR"));
		cities.add(new City("Paris", "FR"));
		cities.add(new City("Corfu", "GR"));
		cities.add(new City("Florida", "US"));
		cities.add(new City("Berlin", "DE"));
		cities.add(new City("Tokyo", "JP"));
		cities.add(new City("Amsterdam", "NL"));
		cities.add(new City("Brussels", "BE"));
		cities.add(new City("Sydney", "AU"));
		calcData();
	}
	
	public void calcData() {
		double lon1=0,lat1=0;	//Origin city
		double lon2=0,lat2=0;	//Each city
		double maxdist= 15_317.0;
		OpenData od= new OpenData();
		
		for(City city: cities) {
			od.fetchData(city.getName(), city.getCountry());
			// Calculate criteria			
			for(Criteria c: Criteria.values()) {
				double t= od.criterionFrequency(od.getArticle(), c.toString());
				city.getTerms()[c.idx()]= scale(t, TERM_MIN, TERM_MAX);
			}
			city.getTerms()[7]= scale(od.getTemp(), TEMP_MIN, TEMP_MAX);
			city.getTerms()[8]= Double.valueOf(od.getClouds().getAll())/100.0;
			if(city.getName().equals("Athens")) {
				city.getTerms()[9]= 0.0;
				lat1= od.getCoord().getLat();
				lon1= od.getCoord().getLon();
			}else {
				lat2= od.getCoord().getLat();
				lon2= od.getCoord().getLon();
				double dist= DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K");
				city.getTerms()[9]= scale(dist, 0.0, maxdist);
			}
		}
	}

}
