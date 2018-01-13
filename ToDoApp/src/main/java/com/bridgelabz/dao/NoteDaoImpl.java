package com.bridgelabz.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Label;
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
		logger.info("dao entered");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Note> query = session.createQuery("from Note where userid=:id");
		Criteria criteria = session.createCriteria(Note.class);
		criteria.createAlias("Collaborator", "c");
		criteria.add(Restrictions.eq("c.id", id));
		List<Note> notes1 = criteria.list();
		query.setParameter("id", id);
		List<Note> notes = query.getResultList();
		
		// notes.removeAll(notes1);
		notes.addAll(notes1);

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
		Collection<User> userlist = new HashSet<User>();
		userlist = note.getCollaborator();
		userlist.add(user);
		note.setCollaborator(userlist);
		session.save(note);
		session.getTransaction().commit();
		session.close();

	}

	public List<User> getCollaborator(int id, User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		logger.info(id);
		Note note = (Note) session.get(Note.class, id);
		System.out.println(note.getTitle());
		List<User> userlist = (List<User>) note.getCollaborator();
		for (int i = 0; i < userlist.size(); i++) {
			User usercheck = userlist.get(i);
			logger.warn("usercheck entered" + user.getEmail());
			if (usercheck.getId() == user.getId()) {
				userlist.remove(i);
			}
		}

		logger.info(user.getId());
		session.close();
		return userlist;

	}

	public void setCollabnotes(int id, Note note) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, id);
		Collection<Note> collabnotes = new HashSet<Note>();
		collabnotes = user.getCollabNotes();
		collabnotes.add(note);
		user.setCollabNotes(collabnotes);

		session.save(user);
		session.getTransaction().commit();
		session.close();
	}

	public List<Note> getCollabnotes(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		logger.info(id);
		User user = (User) session.get(User.class, id);
		System.out.println(user.getEmail());
		List<Note> notelist = (List<Note>) user.getCollabNotes();

		session.close();
		return notelist;

	}

	public void removeCollaborator(int id, User user) {
		logger.warn("entered herre");
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Note note = (Note) session.get(Note.class, id);
		user = (User) session.get(User.class, user.getId());
		note.getCollaborator().remove(user);

		session.update(note);
		session.getTransaction().commit();
		session.close();
	}

	public void createLabel(User user, Label label) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		label.setUser(user);
		session.save(label);
		session.getTransaction().commit();
		session.close();
		

	}

	public void attachLabelToNote(Note note, Label label) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void attachLabelToNote(Note note, Label label) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Label updatedlabel= (Label)session.get(Label.class, label.getId());
		label.setNote(note);
		session.update(updatedlabel);
		session.getTransaction().commit();
		session.close();
		

	}*/

	
}
