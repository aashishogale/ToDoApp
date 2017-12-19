package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;

/**
 * @author aashish
 *
 */

public interface UserService {

	/**
	 * @param user
	 * @purpose register the user
	 * 
	 */
	public boolean register(User user);

	/**
	 * @param login
	 * @return user-validated user
	 */
	public User validateUser(Login login);

	public void generateOtp(String email);

	public boolean validateOtp(String email, int otp);

	public String generateToken(int id);

	public int checkToken(String token);
}
