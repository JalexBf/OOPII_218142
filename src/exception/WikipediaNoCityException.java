package exception;

public class WikipediaNoCityException extends Exception {
	private static final long serialVersionUID = 1L;
	//Students will implement it.
	private String cityName;
	
	public WikipediaNoCityException(String cityName) {
		this.cityName= cityName;
	}
	
	public String getMessage() {
		return "There is not any city named "+cityName+".";
	}
}
