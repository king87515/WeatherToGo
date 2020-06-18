package ntou.cs.wbse.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntou.cs.wbse.model.SightseeHandler;
import ntou.cs.wbse.entity.*;
import ntou.cs.wbse.repository.NoteRepository;

@Service
public class InfoService {

	@Autowired
	private SightseeHandler handler;
	
	@Autowired
	private NoteRepository repository;
	
	public InfoService(SightseeHandler handler, NoteRepository repository) {
		this.handler = handler;
		this.repository = repository;
		
		
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
	
	public List<Info> getInfos(String add, String name){ //String region,String town,String keyword
//		System.out.println(repository.findAll().size());
		
		for(int i=0; i<repository.findAll().size();i++) {
			handler
				.getInfo(repository.findAll().get(i).getId())
				.setNote(repository.findAll().get(i).getNote());
		}

		
		return handler.findInfos(add, name);
	}
	
	public Info getInfo(String id) {
		Info info = handler.getInfo(id);
		
		for(int i=0; i<repository.findAll().size();i++) {
			handler
				.getInfo(repository.findAll().get(i).getId())
				.setNote(repository.findAll().get(i).getNote());
		}

		return info;

	}
	
//	public Info getAA(String region) {
//		Info info = handler.getAA(region);
//		
//		for(int i=0; i<repository.findAll().size();i++) {
//			handler
//				.getInfo(repository.findAll().get(i).getId())
//				.setNote(repository.findAll().get(i).getNote());
//		}
//
//		return info;
//
//	}
	
	public Note createNote(String id, String noteContent) { //Post
		Info info = handler.getInfo(id);
		
		Note note = new Note();
		note.setId(info.getId());
		note.setNote(noteContent);
		
		handler.getInfo(info.getId()).setNote(noteContent);
		
		return repository.insert(note);
		
	}
	
	public Note replaceNote(String id, String noteContent) { //Put
		Info info = handler.getInfo(id);
		
		Note note = new Note();
		note.setId(info.getId());
		note.setNote(noteContent);
		
		handler.getInfo(info.getId()).setNote(noteContent);
		
		
		return repository.save(note);
		
	}
	
	public void deleteNote(String id) { //delete
		//delete 目前怪怪的
		Info info = handler.getInfo(id);
		info.setNote(null);
		repository.deleteById(id);
		
	}
}
