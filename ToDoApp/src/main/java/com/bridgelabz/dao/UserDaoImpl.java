package com.bridgelabz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	JdbcTemplate jdbcTemplate;
	@Autowired
	BCryptPasswordEncoder encryptor;

	public boolean register(User user) {
		String sql = "insert into register1(name,password,email,mobile) values(?,?,?,?)";

		String password = encryptor.encode(user.getPassword());
		int a = jdbcTemplate.update(sql,
				new Object[] { user.getFname(), password, user.getEmail(),user.getNumber() });
		if (a == 0) {
			logger.warn("registration unsuccesful");
			return false;

		} else {
			logger.info("registration successful");
			return true;
		}
	}

	public User validateUser(Login login) {

		String sql = "select * from register1";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		Iterator<User> itr = users.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			if (user.getEmail().equals(login.getEmail())
					&& encryptor.matches(login.getPassword(), user.getPassword())) {
				return user;
			}

		}
		logger.warn("user not present");
		return null;
	}

	public boolean checkEmail(String email) {

		String sql = "select * from register1";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		Iterator<User> itr = users.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			if (user.getEmail().equals(email)) {
				return true;
			}

		}
		logger.warn("user not present");
		return false;
	}

	public void changePassword(String email, String password) {
		String enpassword = encryptor.encode(password);

		String sql = "update register1 set password='" + enpassword + "' where email='" + email + "'";
		jdbcTemplate.update(sql);

	}

}

class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();

		user.setPassword(rs.getString("password"));
		user.setFname(rs.getString("name"));
	
		user.setEmail(rs.getString("email"));
	

		return user;
	}

	
	
}
