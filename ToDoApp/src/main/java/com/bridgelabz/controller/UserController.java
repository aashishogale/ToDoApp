package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.CustomResponse;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService userService;
	private static final Logger logger = Logger.getLogger("UserController");

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user) {

		logger.info(user.getEmail() + user.getFname() + user.getNumber() + user.getPassword());
		try {
			if (userService.register(user)) {
				logger.info("save confirmed");

				return new ResponseEntity<User>(HttpStatus.OK);

			} else {
				System.out.print("error in registration");
				return new ResponseEntity<User>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.BAD_GATEWAY);
		}

	}

	
	@RequestMapping(value = "Login", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> Login(@RequestBody Login login, HttpServletRequest request) {

		User user = userService.validateUser(login);
		CustomResponse response = new CustomResponse();
		if (user != null) {
			String token = userService.generateToken(user.getId());

			System.out.print(token);
            request.setAttribute("token", token);
			response.setMessage(token);
			response.setCode(1);

			return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("Bad credentials");
			response.setCode(-1);
			return new ResponseEntity<CustomResponse>(response, HttpStatus.CONFLICT);

		}
	}

	@RequestMapping(value = "LoginCheck", method = RequestMethod.POST)
	public ResponseEntity<Login> LoginCheck(@RequestBody Login login, HttpServletRequest request,
			@RequestHeader(name = "Token") String Token) {
		User user = userService.validateUser(login);
		if (userService.checkToken(Token) == user.getId()) {
			logger.info("token validated");

			return new ResponseEntity<Login>(HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Login>(HttpStatus.LOCKED);

		}

	}
}
