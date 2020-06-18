package ntou.cs.wbse.model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;

import ntou.cs.wbse.entity.Location;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherHandler {
	private static final String dataURL = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-001?Authorization=CWB-CCC4473B-8F1B-4473-B817-5E3DC8C279EF&downloadType=WEB&format=JSON";
	
	private List<Location> locationList;
	public String produceJsonFromURL(String requestURL) throws IOException {
		try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
			//System.out.println(scanner);
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
			//return scanner.next();
		}
	}
	
	private List<Location> convertToObject(String jsonData){
		Gson gson = new Gson();
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String k = gson.toJson(jsonData);
		ArrayList<Location> placeList = new ArrayList<Location>();
		ArrayList<Location> test = new ArrayList<Location>();
		try {
			
//			test = gson.fromJson(jsonData, new TypeToken<List<Location>>() {}.getType());
//			System.out.println(test);
			
			
//			System.out.println(jsonData.getClass().toString());
			List<String> myJson = JsonPath.read(jsonData, "$.cwbopendata.dataset.location");
//			System.out.println(myJson);
			String j = gson.toJson(myJson);
//			System.out.println(j);
//			List<Object> objectList = new ArrayList<Object>(myJson);
//			String MyJsonData = gson.toJson(MyJson);
//			System.out.println(MyJsonData);
			Type listType = new TypeToken<List<Location>>() {
			}.getType(); // java.util.List<ntou.cs.wbse.entity.Location>
			placeList = gson.fromJson(j, listType);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}
		return placeList;
	}
	
	public List<Location> findLocations(String queryName){
		List<Location> matchingElements;
		if (queryName.equals("")) {
			matchingElements = locationList.stream().collect(Collectors.toList());

		} else {
			matchingElements = locationList.stream().filter(
					str -> str.getLocationName().trim().contains(queryName))
					.collect(Collectors.toList());
		}
		
		return matchingElements;
	}
	
	public Location getLocation(String location) {
		List<Location> matchingElements = locationList.stream().filter(str -> str.getLocationName().equals(location))
				.collect(Collectors.toList());
		if ((matchingElements != null) && (matchingElements.size() > 0)) {
			return matchingElements.get(0);
		} else {
			return null;
		}
	}
	
 	public void initialize() throws IOException, URISyntaxException {

		String weatherDataJson = produceJsonFromURL(dataURL);
//		System.out.println(weatherDataJson);
		locationList = convertToObject(weatherDataJson);
		System.out.println(locationList);
//		System.out.println(locationList.get(0));
//		System.out.println(locationList.get(0).getLocationName());
//		System.out.println(locationList.get(0).getWeatherElement());
//		System.out.println(locationList.get(0).getWeatherElement().get(0).getTime().get(0).getParameter().getParameterName());
		System.out.println(findLocations("北"));
	}
}
