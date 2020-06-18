package ntou.cs.wbse.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ntou.cs.wbse.service.LocationService;
import ntou.cs.wbse.entity.*;

@RestController
@RequestMapping("/location")
public class LocationController {
	@Autowired
	private LocationService locationService;
	
	@GetMapping
	public ResponseEntity<List<Location>> getLocations(@RequestParam(required=false,defaultValue="") String locationName){
		List<Location> locations = locationService.getLocations(locationName);
//		System.out.println("sssss");
		return ResponseEntity.ok(locations);
		
	}
	
	@GetMapping(value = "/{locationName}")
	public ResponseEntity<Location> getLocation(@PathVariable("locationName") String locationName){
		Location location = locationService.getLocation(locationName);
//		System.out.println("gggggg");
		return ResponseEntity.ok().body(location);
		
	}
	
}

