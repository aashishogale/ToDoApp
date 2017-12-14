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
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.model.Login;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
@RestController
public class LoginController {

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ResponseEntity<Login> Login(@RequestBody Login login, HttpServletRequest request) {

		
		User user = userService.validateUser(login);
		if (user!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			return new ResponseEntity<Login>(HttpStatus.OK);
		} else {

		return new ResponseEntity<Login>(HttpStatus.CONFLICT);
		
		}
	}
}


