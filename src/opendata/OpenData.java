package opendata;
import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import exception.WikipediaNoArcticleException;
import exception.WikipediaNoCityException;
import weather.Clouds;
import weather.Coord;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

/********************************************************************
 * Description and weather information using OpenData with Jackson JSON
 * processor and Jersey Client.
 * 
 * @since 29-2-2020
 * @version 1.0
 * @author John Violos
 ********************************************************************/
public class OpenData {
	private OpenWeatherMap weather=null;
	private Double temp;
	private Coord coord;
	private String article;
	private Clouds clouds;
	
	/**
	 * Retrieves weather information, geotag(lan, lon) and 
	 * a Wikipedia article for a given city using Jersey framework.
	 * 
	 * @param city    The Wikipedia article and OpenWeatherMap city.
	 * @param country The country initials (i.e. gr, it, de).
	 * @param appid   Your API key of the OpenWeatherMap.
	 * @throws WikipediaNoCityException 
	 */
	
	public OpenWeatherMap openWeatherMap(String city, String country, String appid) {
		ClientConfig config= new DefaultClientConfig();
		Client client= Client.create(config);
		WebResource service= client.resource(UriBuilder.fromUri(
				"http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid)
				.build());
		ObjectMapper mapper= new ObjectMapper();
		String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
		
		try {
			weather = mapper.readValue(json, OpenWeatherMap.class);
		} catch (JsonParseException e) {
			
		} catch (JsonMappingException e) {
			
		} catch (IOException e) {
			
		}
		return weather;
	}

	/****************************************************************
	 * Retrieves Wikipedia information, geotag(lan, lon) and a Wikipedia article
	 * for a given city using Jersey framework.
	 * 
	 * @param city The Wikipedia article and OpenWeatherMap city.
	 * @throws WikipediaNoArcticleException
	 ****************************************************************/
	public String Wikipedia(String city) throws WikipediaNoArcticleException, WikipediaNoCityException {
		String article = "";
		ClientConfig config= new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service= client.resource(
			UriBuilder.fromUri(
				"https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="
				+ city + "&format=json&formatversion=2").build()
		);
		ObjectMapper mapper= new ObjectMapper();
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		if(json.contains("pageid")) {
			MediaWiki mediaWiki_obj=null;
			try {
				mediaWiki_obj = mapper.readValue(json, MediaWiki.class);
			} catch (JsonParseException e) {
				
				throw new WikipediaNoCityException(city);
			
			} catch (JsonMappingException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			article = mediaWiki_obj.getQuery().getPages().get(0).getExtract();
		}else{
			throw new WikipediaNoArcticleException(city);
		}
		return article;
	}
	
	//===============================================================
	public static int countTotalWords(String str) {
		String s[] = str.split(" ");
		return s.length;
	}

	/****************************************************************
	 * Counts the number of times a criterion occurs in the city wikipedia article.
	 * 
	 * @param cityArticle The String of the retrieved wikipedia article.
	 * @param criterion   The String of the criterion we are looking for.
	 * @return An integer, the number of times the criterion-string occurs in the
	 *         wikipedia article.
	 ****************************************************************/
	public int criterionFrequency(String cityArticle, String criterion) {
		cityArticle= cityArticle.toLowerCase();
		int index = cityArticle.indexOf(criterion);
		int count = 0;
		while(index != -1) {
			count++;
			cityArticle= cityArticle.substring(index+1);
			index = cityArticle.indexOf(criterion);
		}
		return count;
	}
	
	//===============================================================
	public void fetchData(String city, String country) {
		OpenData od= new OpenData();
		String appid = "262f78f37142c4978aec6d362eaf4eff"; // My openWeatherMap id.
		weather= od.openWeatherMap(city, country, appid);
		temp = weather.getMain().getTemp();
		coord= weather.getCoord();
		clouds=weather.getClouds();
		try {
			article= od.Wikipedia(city);
		} catch (WikipediaNoArcticleException e) {
			System.out.println("No Article");
		} catch (WikipediaNoCityException e) {
			System.out.println("No City");
		}
	}	
	

	public Double getTemp() {return temp;}
	public Coord getCoord() {return coord;}
	public Clouds getClouds() {return clouds;}
	public String getArticle() {return article;}
	
}