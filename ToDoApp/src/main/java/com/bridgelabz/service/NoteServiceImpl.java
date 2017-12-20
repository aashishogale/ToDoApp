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

	public List<Note> getNoteList(User user) {
		return notedao.getNoteList(user);
	}

	public Note getNotebyId(int id) {
		return notedao.getNotebyId(id);
	}

}
