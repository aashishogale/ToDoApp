package com.bridgelabz.service;

import java.util.Date;
import java.util.List;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

public interface NoteService {
	public void createNote(User user, Note note);

	public void deleteNote(Note note);

	public void updateNote(Note note);

	public List<Note> getNoteList(int id);

	public Note getNotebyId(int id);

	public void pinNote(int id);

	public void trashNote(int id);

	public void archiveNote(int id);

	public void setReminder(int id, Date reminder);

	public void deleteReminder(int id);

	public void setColor(int id, String color);
	public void addCollaborator(int id,User user);
	public List<User> getCollaborator(int id,User user);
	public void setCollabnotes(int id,Note note);
	public List<Note> getCollabnotes(int id);
	public void removeCollaborator(int id,User user);
	public void createLabel(User user, Label label);
	public void attachLabelToNote(Note note, Label label) ;
}
