package tacos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Endpoint(id = "notes", enableByDefault = true)
public class NoteEndpoint {
	private List<Note> notes = new ArrayList<>();
	
	@ReadOperation
	public List<Note> notes() {
		return notes;
	}
	
	@WriteOperation
	public List<Note> addNote(String text) {
		notes.add(new Note(text));
		return notes;
	}
	
	@DeleteOperation
	public List<Note> deleteNote(int index) {
		if (index < notes.size()) {
			notes.remove(index);
		}
		return notes;
	}
	
	@RequiredArgsConstructor
	private class Note {
		@Getter
		private LocalDateTime time = LocalDateTime.now();
		
		@Getter
		private final String text;
	}
}
