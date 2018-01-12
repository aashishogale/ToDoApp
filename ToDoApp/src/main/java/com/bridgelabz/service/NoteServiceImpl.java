package com.bridgelabz.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDao notedao;

	private static final Logger logger = Logger.getLogger(NoteServiceImpl.class);

	public void createNote(User user, Note note) {
		Date date = new Date();
		note.setDate(date);
		notedao.createNote(user, note);

	}

	public void deleteNote(Note note) {
		notedao.deleteNote(note);

	}

	public void updateNote(Note note) {
		Date date = new Date();
		logger.info(date);
		note.setDate(date);
		notedao.updateNote(note);

	}

	public List<Note> getNoteList(int id) {
		return notedao.getNoteList(id);
	}

	public Note getNotebyId(int id) {
		return notedao.getNotebyId(id);
	}

	public void pinNote(int id) {
		notedao.pinNote(id);

	}

	public void trashNote(int id) {
		notedao.trashNote(id);

	}

	public void archiveNote(int id) {
		notedao.archiveNote(id);

	}

	public void setReminder(int id, Date reminder) {
		notedao.setReminder(id, reminder);
	}

	public void deleteReminder(int id) {
		notedao.deleteReminder(id);

	}

	public void setColor(int id, String color) {
		notedao.setColor(id, color);

	}

	public void addCollaborator(int id, User user) {
		// TODO Auto-generated method stub
		notedao.addCollaborator(id, user);
	}

	public List<User> getCollaborator(int id, User user) {
		// TODO Auto-generated method stub
		return notedao.getCollaborator(id, user);

	}

	public void setCollabnotes(int id, Note note) {
		// TODO Auto-generated method stub
		notedao.setCollabnotes(id, note);
	}

	public List<Note> getCollabnotes(int id) {
		// TODO Auto-generated method stub
		return notedao.getCollabnotes(id);
	}

	public void removeCollaborator(int id, User user) {

		notedao.removeCollaborator(id, user);
	}

	public void createLabel(User user, Label label) {
		notedao.createLabel(user, label);

	}

	public void attachLabelToNote(Note note, Label label) {
		notedao.attachLabelToNote(note, label);
		// TODO Auto-generated method stub
		
	}

}
