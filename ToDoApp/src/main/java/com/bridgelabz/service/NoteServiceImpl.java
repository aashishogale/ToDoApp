package com.bridgelabz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDao notedao;
	Date date = new Date();

	public void createNote(User user, Note note) {

		note.setDate(date);
		notedao.createNote(user, note);

	}

	public void deleteNote(Note note) {
		notedao.deleteNote(note);

	}

	public void updateNote(Note note) {
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

}
