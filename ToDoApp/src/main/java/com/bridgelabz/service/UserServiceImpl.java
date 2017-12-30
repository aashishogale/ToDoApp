package com.bridgelabz.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

	@Autowired
	private MailSetter mailSetter;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final Logger logger = Logger.getLogger(UserService.class);
	private static String key = "QwErTyUiOp";

	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	@Transactional(rollbackFor = Exception.class)
	public boolean register(User user) {

		user.setVerified(false);
		boolean saved = userDao.register(user);
		logger.info("user saved");
		// if (saved == true) {
		mailSetter.sendMail(user.getEmail());
		logger.info("mail sent");

		// }

		return saved;

	}

	@Transactional(rollbackFor = Exception.class)
	public User registerSocial(User user) {

		User usersoc = userDao.registerSocial(user);
		logger.info("user saved");

		return usersoc;

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

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public String generateToken(int id) {
		long currentTime = System.currentTimeMillis();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, 10);

		Date currentDate = new Date(currentTime);
		Date expirationDate = cal.getTime();
		System.out.println(currentDate);
		String token = Jwts.builder().setId(Integer.toString(id)).setExpiration(expirationDate).setIssuedAt(currentDate)
				.signWith(SignatureAlgorithm.HS256, key).compact();

		redisTemplate.setEnableTransactionSupport(true);

		redisTemplate.opsForValue().set("token", token);

		return token;

	}

	public boolean checkToken(String token) {

		int id = 0;
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		System.out.println("ID: " + claims.getId());
		id = Integer.parseInt(claims.getId());
		if (userDao.getUserById(id) != null)
			return true;
		else
			return false;
	}

	public int getidbyToken(String token) {
		int id = 0;
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		System.out.println("ID: " + claims.getId());
		id = Integer.parseInt(claims.getId());
		return id;
	}

	@Transactional
	public void updateVerifyUser(User user) {
		userDao.updateVerifyUser(user);

	}

	public String getTokenFromRedis() {
		String token=redisTemplate.opsForValue().get("token");
		return token ;
	}
	

}
