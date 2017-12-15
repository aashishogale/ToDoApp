package com.bridgelabz.service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.controller.MailSetter;
import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;

/**
 * @author aashish implementing the userservice methods
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private MailSender mailSender;
	private static final Logger logger = Logger.getLogger(UserService.class);

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

	public void changePassword(String email, String password) {
		userDao.changePassword(email, password);
	}

	public boolean checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	public void generateToken(int id, String type) {
		// TODO Auto-generated method stub
		redisTemplate.setEnableTransactionSupport(true);
		String uid = UUID.randomUUID().toString();
		if (type.equals("ACCESS")) {
			redisTemplate.opsForValue().set(Integer.toString(id) + type, uid);
			redisTemplate.expire(Integer.toString(id) + type, 2L, TimeUnit.SECONDS);
			logger.info("accesstoken created");
		} else {
			redisTemplate.opsForValue().set(Integer.toString(id) + type, uid);
			redisTemplate.expire(Integer.toString(id) + type, 10L, TimeUnit.MINUTES);
			logger.info("refresh token  created");
		}

	}

	public boolean checkToken(int id) {
		final String access = "ACCESS";
		final String refresh = "REFRESH";
		String accesskey = Integer.toString(id) + access;
		String refreshkey = Integer.toString(id) + refresh;
		String uid = (String) redisTemplate.opsForValue().get(accesskey);
		System.out.println(uid);
		if (uid == null) {
			logger.info("access token expired");
			uid = (String) redisTemplate.opsForValue().get(refreshkey);
			if (uid != null) {

				this.generateToken(id, access);
				return true;
			} else {
				logger.info("refresh token expired");
				return false;
			}
		}
		logger.info("all expired");
		return true;

	}

}
