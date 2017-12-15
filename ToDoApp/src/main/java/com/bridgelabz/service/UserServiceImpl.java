package com.bridgelabz.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.controller.MailSetter;
import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author aashish implementing the userservice methods
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	private MailSender mailSender;
	private static final Logger logger = Logger.getLogger(UserService.class);
	private static String key = "QwErTyUiOp";

	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	@Transactional(rollbackFor = Exception.class)
	public boolean register(User user, HttpServletRequest request) {
		try {
			boolean saved = userDao.register(user);

			MailSetter mailSetter = new MailSetter();
			mailSetter.setMailSender(mailSender);
			mailSetter.sendMail(user.getEmail());

			return saved;
		} catch (Exception ie) {
			logger.warn("invalid information");
		}
		return false;
	}

	@Transactional
	public User validateUser(Login login) {
		return userDao.validateUser(login);
	}

	public void generateOtp(String email) {
		int randomnum = (int) (1000 + Math.random() * (9999 - 1000));

		hm.put(email, randomnum);
		MailSetter mailSetter = new MailSetter();
		mailSetter.setMailSender(mailSender);
		mailSetter.sendOtp(email, randomnum);

	}

	public boolean validateOtp(String email, int otp) {
		if (hm.containsKey(email) && hm.containsValue(otp)) {
			hm.clear();
			return true;
		}
		return false;

	}



	public String generateToken(int id) {

		long currentTime = System.currentTimeMillis();
		Date currentDate = new Date(currentTime);
		System.out.println(currentDate);
		String generatedToken = Jwts.builder().setId(Integer.toString(id)).setIssuedAt(currentDate)
				.signWith(SignatureAlgorithm.HS256, key).compact();
		return generatedToken;

	}

	public int checkToken(String token) {
		int id = 0;
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		System.out.println("ID: " + claims.getId());
		id = Integer.parseInt(claims.getId());
		return id;

	}

}
