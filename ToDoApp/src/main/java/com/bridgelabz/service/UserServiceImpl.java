package com.bridgelabz.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.*;

import com.bridgelabz.controller.MailSetter;
import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;
import javax.crypto.spec.SecretKeySpec;

import java.security.Key;


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

	public static 	HashMap<String, Integer> hm=new HashMap<String,Integer>();

	@Transactional(rollbackFor=Exception.class)
	public boolean register(User user, HttpServletRequest request) {
     try {
		boolean saved = userDao.register(user);
		
		MailSetter mailSetter=new MailSetter();
		mailSetter.setMailSender(mailSender);
		mailSetter.sendMail(user.getEmail());
		
		
		return saved;
		}
		catch(Exception ie) {
		logger.warn("invalid information");
		}
     return false;
	}

	@Transactional
	public User validateUser(Login login) {
		return userDao.validateUser(login);
	}
	public void generateOtp(String email){
		int randomnum = (int) (1000 + Math.random() * (9999 - 1000));
		
		
	
		hm.put(email, randomnum);
		MailSetter mailSetter=new MailSetter();
		mailSetter.setMailSender(mailSender);
		mailSetter.sendOtp(email, randomnum);
		
		
		
	}
	public boolean validateOtp(String email,int otp) {
		if(hm.containsKey(email)&&hm.containsValue(otp)) {
			hm.clear();
			return true;
		}
		return false;
		
	}
	public void changePassword(String email,String password) {
		userDao.changePassword(email,password);
	}
	public boolean checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	public String generateToken(){
		JwtBuilder builder=Jwts.builder().
	
	}




}
