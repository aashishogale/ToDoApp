package com.bridgelabz.dao;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

public interface NoteDao {
	public void createNote(User user,Note note);
	public void deleteNote(Note note);
	public void updateNote(Note note);

}
