package com.bridgelabz.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	DataSource datasource;

	@Autowired
	BCryptPasswordEncoder encryptor;
	@Autowired
	private SessionFactory sessionFactory;

	public boolean register(User user) {
		logger.info("register entered");
		logger.info("user info" + user.getFname());
		if (user.getPassword() != null) {
			logger.info("password");
			String password = encryptor.encode(user.getPassword());
			user.setPassword(password);
		}
		Session session = sessionFactory.openSession();
		if (this.getUserByEmail(user.getEmail()) == null) {
			session.beginTransaction();

			session.save(user);

			session.getTransaction().commit();
			System.out.println("Inserted Successfully");
			session.close();
			return true;
		} else {
			return false;

		}

	}

	public User registerSocial(User user) {
		logger.info("register entered");

		Session session = sessionFactory.openSession();
		if (this.getUserByEmail(user.getEmail()) == null) {
			session.beginTransaction();

			session.save(user);

			session.getTransaction().commit();
			System.out.println("Inserted Successfully");
			session.close();
			return user;
		}

		return this.getUserByEmail(user.getEmail());
	}

	public User validateUser(Login login) {

		Session session = sessionFactory.openSession();
		// getting transaction object from session object
		session.beginTransaction();
		Query<User> query = session.createQuery("from User");
		List<User> users = query.getResultList();
		for (User user : users) {

			if (user.getEmail().equals(login.getEmail()) && encryptor.matches(login.getPassword(), user.getPassword())
					&& user.isVerified()) {
				return user;
			}

		}
		logger.warn("user not present");
		return null;
	}

	public User getUserById(int id) {
		Session session = sessionFactory.openSession();
		// getting transaction object from session object
		session.beginTransaction();
		Query<User> query = session.createQuery("from User");
		List<User> users = query.getResultList();
		for (User user : users) {

			if (user.getId() == id) {
				return user;
			}

		}
		logger.warn("user not present");
		return null;
	}

	public User getUserByEmail(String email) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query<User> query = session.createQuery("from User");
		List<User> users = query.getResultList();
		for (User user : users) {

			if (user.getEmail().equals(email)) {
				return user;
			}

		}
		logger.warn("user not present");
		return null;
	}

	public User updateVerifyUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User updatedUser = (User) session.get(User.class, user.getId());
		updatedUser.setVerified(true);
		session.save(updatedUser);
		System.out.println("Updated Successfully");
		session.getTransaction().commit();

		return user;
	}

}
