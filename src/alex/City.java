package alex;

public class City {
	private String name;
	private String country;
	private double terms[]= new double[10];
	
	
	// Getters - Setters
	public String getName()    {return name;}
	public String getCountry() {return country;}
	public double[] getTerms() {return terms;}
	public void setName(String name) {this.name = name;}
	public void setCountry(String country) {this.country = country;}
	
	
	// Constructor
	public City(String name, String country) {
		super();
		this.name = name;
		this.country = country;
		
	}
	


}
