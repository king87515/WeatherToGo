package ntou.cs.wbse.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "note")
public class Note {
	private String id;
	private String note;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	
}
