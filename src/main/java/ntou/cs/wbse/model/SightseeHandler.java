package ntou.cs.wbse.model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ntou.cs.wbse.entity.Info;

@Component
public class SightseeHandler {
private static final String dataURL = "https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json";

	private List<Info> infoList;
//	private List<Info> list;
	public String produceJsonFromURL(String requestURL) throws IOException {
		try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
			scanner.useDelimiter("\\A");
			String str = scanner.hasNext() ? scanner.next() : "";
			return str.trim().replace("" + '\uFEFF', "");
		}
	}
	
	private List<Info> convertToObject(String jsonData){
		ArrayList<Info> placeList = new ArrayList<Info>();
		Gson gson = new Gson();
		
		try {
			JsonNode activities = new ObjectMapper().readTree(jsonData).get("XML_Head").get("Infos").get("Info");
//			System.out.println("Json:\n" + activities);

			Iterator<JsonNode> iterator = activities.elements();
			while (iterator.hasNext()) {
				JsonNode activity = iterator.next();
//				System.out.println("Name:" + activity.get("Name"));
//				System.out.println("Description:" + activity.get("Description"));
//				System.out.println();
			}
			Type listType = new TypeToken<List<Info>>() {
			}.getType();
			placeList = gson.fromJson(activities.toString(), listType);
//			System.out.println(gson.toJson(placeList));
//			list = gson.fromJson(gson.toJson(placeList),listType);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}
		return placeList;
	}
	
	public List<Info> findInfos(String queryAdd,String queryName){
		List<Info> matchingElements;
//		System.out.println("R:"+queryRegion);
//		System.out.println("T:"+queryTown);
//		System.out.println("K:"+queryKeyword);
//		System.out.println(infoList.stream().filter(str -> str.getKeyword().trim().contains(queryKeyword)).collect(Collectors.toList()));
//		if (queryRegion.equals("") && queryTown.equals("") && queryKeyword.equals("")) {
//			matchingElements = infoList.stream().collect(Collectors.toList());
//
//		} else if (queryRegion.equals("") && queryTown.equals("")) {
//			matchingElements = infoList.stream().filter(
//					str -> str.getKeyword().trim().contains(queryKeyword))
//					.collect(Collectors.toList());
//		} else if (queryRegion.equals("") && queryKeyword.equals("")) {
//			matchingElements = infoList.stream().filter(
//					str -> str.getTown().trim().contains(queryTown))
//					.collect(Collectors.toList());
//		} else if (queryTown.equals("") && queryKeyword.equals("")) {
//			matchingElements = infoList.stream().filter(
//					str -> str.getRegion().equals(queryRegion))
//					.collect(Collectors.toList());
//		} else if (queryRegion.equals("")) {
//			matchingElements = infoList.stream().filter(
//					str -> str.getTown().trim().contains(queryTown) 
//							&& str.getKeyword().trim().contains(queryKeyword))
//					.collect(Collectors.toList());
//		} else if (queryTown.equals("")) {
//			matchingElements = infoList.stream().filter(
//					str -> str.getRegion().trim().contains(queryRegion) 
//							&& str.getKeyword().trim().contains(queryKeyword))
//					.collect(Collectors.toList());
//		} else if (queryKeyword.equals("")){
//			matchingElements = infoList.stream().filter(
//					str -> str.getRegion().trim().contains(queryRegion) 
//							&& str.getTown().trim().contains(queryTown))
//					.collect(Collectors.toList());
//		} else {
//			matchingElements = infoList.stream().filter(
//					str -> str.getRegion().trim().contains(queryRegion) 
//							&& str.getTown().trim().contains(queryTown) 
//							&& str.getKeyword().trim().contains(queryKeyword))
//					.collect(Collectors.toList());
//		}
		if (queryAdd.equals("") && queryName.equals("")) {
			matchingElements = infoList.stream().collect(Collectors.toList());

		} else if(queryAdd.equals("")) {
			matchingElements = infoList.stream().filter(
					str -> str.getName().trim().contains(queryName))
					.collect(Collectors.toList());
		} else if(queryName.equals("")) {
			matchingElements = infoList.stream().filter(
					str -> str.getAdd().trim().contains(queryAdd))
					.collect(Collectors.toList());
		} else {
			matchingElements = infoList.stream().filter(
					str -> str.getAdd().trim().contains(queryAdd) && str.getName().trim().contains(queryName))
					.collect(Collectors.toList());
		}	
		return matchingElements;
	}
	
	public Info getInfo(String id) {
		List<Info> matchingElements = infoList.stream().filter(str -> str.getId().equals(id))
				.collect(Collectors.toList());
		if ((matchingElements != null) && (matchingElements.size() > 0)) {
			return matchingElements.get(0);
		} else {
			return null;
		}
	}
	
	public Info getAA(String temp) {
		List<Info> matchingElements = infoList.stream().filter(str -> str.getName().contains(temp))
				.collect(Collectors.toList());
		if ((matchingElements != null) && (matchingElements.size() > 0)) {
			return matchingElements.get(0);
		} else {
			return null;
		}
	}
	
 	public void initialize() throws IOException, URISyntaxException {

 		String activityDataJson = produceJsonFromURL(dataURL);
		//activityDataJson = activityDataJson.trim().replace("" + '\uFEFF', "");
		// System.out.println(activityDataJson);
		infoList = convertToObject(activityDataJson);
//		System.out.println("List:" + infoList);
//		System.out.println(InfoList.get(0).getId());
//		System.out.println("list:"+list);
//		System.out.println(findInfos("","",""));
//		System.out.println(getAA("工廠"));
//		System.out.println(findInfos("桃園市","",""));
	}

}
