package ntou.cs.wbse.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ntou.cs.wbse.entity.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	List<Note> findByIdContainingIgnoreCase(String id); 

}