package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService userService;
	private static final Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user, HttpServletRequest request) {

		System.out.println(user.getEmail()+user.getFname()+user.getNumber()+user.getPassword());
		try {
			if (userService.register(user, request)) {
	            logger.info("save confirmed");
				HttpSession session = request.getSession(true);

				session.setAttribute("message", "session created");

				return new ResponseEntity<User>(HttpStatus.OK);

			} else {
				System.out.print("error in registration");
				return new ResponseEntity<User>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			System.out.println("Shit...!!!");
			return new ResponseEntity<User>(HttpStatus.BAD_GATEWAY);
		}

	}
	
	@RequestMapping(value = "Login", method = RequestMethod.POST)
	public ResponseEntity<Login> Login(@RequestBody Login login, HttpServletRequest request) {

		
		User user = userService.validateUser(login);
		if (user!=null) {
			String token=userService.generateToken(user.getId());
			
			System.out.print(token);
			
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			return new ResponseEntity<Login>(HttpStatus.OK);
		} else {

		return new ResponseEntity<Login>(HttpStatus.CONFLICT);
		
		}
	}
	
	@RequestMapping(value = "LoginCheck", method = RequestMethod.POST)
	public ResponseEntity<Login> LoginCheck(@RequestBody Login login, HttpServletRequest request,@RequestHeader(name="Token") String Token) {
		User user = userService.validateUser(login);
		if(userService.checkToken(Token)==user.getId()) {
			logger.info("token validated");
			
    	  return new ResponseEntity<Login>(HttpStatus.FOUND); 
      }
      else {
    	  return new ResponseEntity<Login>(HttpStatus.LOCKED); 
    	  
      }
    	  
	
	}
}
