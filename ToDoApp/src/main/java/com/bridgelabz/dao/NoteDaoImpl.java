package com.bridgelabz.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

@Repository
public class NoteDaoImpl implements NoteDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	DataSource datasource;
	Collection<Note> noteList;

	public void createNote(User user, Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User updatedUser = (User) session.get(User.class, user.getId());
		noteList = new ArrayList<Note>();
		noteList.add(note);
		updatedUser.setNotes(noteList);
		session.save(updatedUser);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteNote(Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(note);
		session.getTransaction().commit();
		session.close();

	}

	public void updateNote(Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note updatedNote = (Note) session.get(Note.class, note.getId());
		updatedNote.setDescription(note.de);(true);
		session.save(updatedUser);
		System.out.println("Updated Successfully");
		session.getTransaction().commit();

		sessionFactory.close();
		return user;
	}

}
