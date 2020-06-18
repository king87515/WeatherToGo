package ntou.cs.wbse.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

import ntou.cs.wbse.service.InfoService;
import ntou.cs.wbse.entity.*;

@RestController
@RequestMapping("/info")
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	
	@GetMapping
	public ResponseEntity<List<Info>> getInfos(@RequestParam(required=false,defaultValue="") String address
				,@RequestParam(required=false,defaultValue="") String name)throws IOException, URISyntaxException{
		List<Info> infos = infoService.getInfos(address, name);
//		System.out.println("sssss");
		return ResponseEntity.ok(infos);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Info> getInfo(@PathVariable("id") String id){
		Info info = infoService.getInfo(id);
//		System.out.println("gggggg");
		return ResponseEntity.ok().body(info);
		
	}
	
//	@GetMapping(value = "/k/{region}")
//	public ResponseEntity<Info> getAA(@PathVariable("region") String region){
//		Info info = infoService.getAA(region);
////		System.out.println("gggggg");
//		return ResponseEntity.ok().body(info);
//		
//	}
	
	@PostMapping(value = "/{id}/note")
	public ResponseEntity<Note> createNote(@PathVariable("id") String id,
			 @RequestBody String noteContent){
		Note note = infoService.createNote(id, noteContent);
		
		URI info = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}/note")
				.buildAndExpand(note.getId())
				.toUri();
		
		return ResponseEntity.created(info).body(note);
	}
	
	@PutMapping(value = "/{id}/note")
	public ResponseEntity<Note> replaceNote(@PathVariable("id") String id,  
				@RequestBody String noteContent){
		Note note = infoService.replaceNote(id, noteContent);
		
		return ResponseEntity.ok(note);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable("id") String id){
		infoService.deleteNote(id);
		return ResponseEntity.noContent().build();
	}
}

