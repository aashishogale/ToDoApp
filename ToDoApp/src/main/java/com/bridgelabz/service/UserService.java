package com.bridgelabz.service;

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
	public User registerSocial(User user);

	public User validateUser(Login login);

	public void generateOtp(String email);

	public boolean validateOtp(String email, int otp);

	public String generateToken(int id);

	public boolean checkToken(String token);

	public int getidbyToken(String token);

	public User getUserById(int id);

	public void updateVerifyUser(User user);
	public String getTokenFromRedis();
	public User getUserByEmail(String email);
	
}
