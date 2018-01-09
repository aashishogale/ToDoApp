package com.bridgelabz.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	private static final Logger logger = Logger.getLogger("NoteDaoImpl");

	public void createNote(User user, Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		note.setUser(user);
		session.save(note);
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
		updatedNote.setDescription(note.getDescription());
		updatedNote.setDate(note.getDate());
		updatedNote.setTitle(note.getTitle());
		session.save(updatedNote);
		System.out.println("Updated Successfully");
		session.getTransaction().commit();
		session.close();

	}

	public List<Note> getNoteList(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Note> query = session.createQuery("from Note where userid=:id");
		query.setParameter("id", id);
		List<Note> notes = query.getResultList();
		return notes;

	}

	public Note getNotebyId(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);

		return note;
	}

	public void pinNote(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);

		note.setPin(!(note.isPin()));

		session.save(note);
		session.getTransaction().commit();
		session.close();

	}

	public void trashNote(int id) {
		logger.info("trash entered");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		note.setTrash(!(note.isTrash()));
		session.save(note);
		session.getTransaction().commit();
		session.close();
	}

	public void archiveNote(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		note.setArchive(!(note.isArchive()));
		session.save(note);
		session.getTransaction().commit();
		session.close();

	}

	public void setReminder(int id, Date reminder) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		note.setReminder(reminder);
		logger.info(note.getReminder());
		session.save(note);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteReminder(int id) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		note.setReminder(null);
		session.save(note);
		session.getTransaction().commit();
		session.close();
	}

	public void setColor(int id, String color) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		note.setColor(color);
		session.save(note);
		session.getTransaction().commit();
		session.close();

	}

	public void addCollaborator(int id, User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		Collection<User> userlist=new HashSet<User>();
		userlist=note.getCollaborator();
		userlist.add(user);
		note.setCollaborator(userlist);
		session.save(note);
		session.getTransaction().commit();
		session.close();
		
	}

	public List<User> getCollaborator(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		List<User> userlist=(List<User>) note.getCollaborator();
		session.close();
		return userlist;

		
	}

}
