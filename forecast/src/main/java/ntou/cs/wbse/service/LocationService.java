package ntou.cs.wbse.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntou.cs.wbse.model.WeatherHandler;
import ntou.cs.wbse.entity.*;
import ntou.cs.wbse.repository.NoteRepository;

@Service
public class LocationService {

	@Autowired
	private WeatherHandler handler;

	
	public LocationService(WeatherHandler handler) {
		this.handler = handler;
		
		try {
			this.handler.initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Location> getLocations(String location){
		return handler.findLocations(location);
	}
	
	public Location getLocation(String locationName) {
		Location location = handler.getLocation(locationName);
		return location;
	}
	
}
