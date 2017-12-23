package com.bridgelabz.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.CustomResponse;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.util.FBConnection;
import com.bridgelabz.util.FbGraph;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService userService;
	@Autowired
	private MailSetter mailSetter;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private FBConnection fbconnection;
	@Autowired
	UserDao userDao;
	private static final Logger logger = Logger.getLogger("UserController");

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user, HttpServletRequest request) {

		logger.info(user.getEmail() + user.getFname() + user.getNumber() + user.getPassword());
		try {
			if (userService.register(user)) {
				logger.info("save confirmed");
				String token = (String) userService.generateToken(user.getId());
				String url = request.getRequestURL() + "/" + token;
				logger.info(url);

				mailSetter.sendUrl(url);
				redisTemplate.opsForValue().set(Integer.toString(user.getId()), token);
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

	@RequestMapping(value = "register/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> activateUser(@PathVariable String token) {
		try {

			if (userService.checkToken(token)) {
				int id = userService.getidbyToken(token);
				User user = userService.getUserById(id);
				userService.updateVerifyUser(user);
				return new ResponseEntity<String>("verified", HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("bad link", HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Token expired", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<String> changepassword(@RequestBody User user, HttpServletRequest request) {

		String newtoken = userService.generateToken(user.getId());
		String url = request.getRequestURL() + "/" + newtoken;
		mailSetter.sendUrl(url);
		return new ResponseEntity<String>("mail sent", HttpStatus.OK);

	}

	@RequestMapping(value = "forgotpassword/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> passwordchange(@PathVariable String token) {
		try {

			if (userService.checkToken(token)) {
				int id = userService.getidbyToken(token);
				User user = userService.getUserById(id);
				userService.updateVerifyUser(user);
				return new ResponseEntity<String>("change password", HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("bad link", HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Token expired", HttpStatus.CONFLICT);
		}
	}
    
	@RequestMapping(value="fbConnect",method=RequestMethod.GET)
	public ResponseEntity<String> fbConnect(HttpServletResponse response) throws IOException {
      String Uri=fbconnection.getAuthURL();
      response.sendRedirect(Uri);
      logger.info("fb page opened");
      return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="redirectFB",method=RequestMethod.GET)
	public void redirectFb(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);
		JSONObject json = new JSONObject(accessToken);
		accessToken=json.getString("access_token");
		FbGraph fbGraph = new FbGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		User user = fbGraph.getGraphData(graph);
		
		userService.register(user);
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/home");
		
	}
}
