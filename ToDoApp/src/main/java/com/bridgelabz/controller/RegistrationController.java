package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	public UserService userService;

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user, HttpServletRequest request) {

		System.out.println(user.getEmail()+user.getFname()+user.getNumber()+user.getPassword());
		if (userService.register(user, request)) {

			HttpSession session = request.getSession(true);

			session.setAttribute("message", "session created");

			return new ResponseEntity<User>(HttpStatus.OK);

		} else {
			System.out.print("error in registration");
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}

	}
}
