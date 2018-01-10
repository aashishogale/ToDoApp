package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;
import com.bridgelabz.service.NoteService;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;
	private static final Logger logger = Logger.getLogger("NoteController");

	@RequestMapping(value = "createnote", method = RequestMethod.POST)
	public ResponseEntity<Note> createNote(@RequestBody Note note, HttpServletRequest request) {

		String token = (String) request.getHeader("token");
		logger.info("create note" + note.getTitle());
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			User user = userService.getUserById(id);
			noteService.createNote(user, note);
			return new ResponseEntity<Note>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "updatenote", method = RequestMethod.POST)
	public ResponseEntity<Note> updateNote(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		logger.info("update note" + note.getId());
		if (userService.checkToken(token)) {
			noteService.updateNote(note);
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "deletenote", method = RequestMethod.POST)
	public ResponseEntity<Note> deleteNote(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			noteService.deleteNote(note);
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "returnnotelist", method = RequestMethod.POST)
	public ResponseEntity<List<Note>> getAllNotesbyUserId(HttpServletRequest request) {
		logger.info("note entered");
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			return new ResponseEntity<List<Note>>(noteService.getNoteList(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Note>>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "getnotebyid", method = RequestMethod.POST)
	public ResponseEntity<Note> getNotebyNoteNd(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			return new ResponseEntity<Note>(noteService.getNotebyId(note.getId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "pinnote", method = RequestMethod.POST)
	public ResponseEntity<String> pinNote(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			noteService.pinNote(note.getId());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "archivenote", method = RequestMethod.POST)
	public ResponseEntity<String> archiveNote(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			noteService.archiveNote(note.getId());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "trashnote", method = RequestMethod.POST)
	public ResponseEntity<String> trashNote(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			noteService.trashNote(note.getId());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "setReminder", method = RequestMethod.POST)
	public ResponseEntity<String> setReminder(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			logger.info(note.getReminder());
			noteService.setReminder(note.getId(), note.getReminder());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "deleteReminder", method = RequestMethod.POST)
	public ResponseEntity<String> deleteReminder(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			noteService.deleteReminder(note.getId());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "setcolor", method = RequestMethod.POST)
	public ResponseEntity<String> setColor(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {

			noteService.setColor(note.getId(), note.getColor());
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "getuserbyid", method = RequestMethod.POST)
	public ResponseEntity<User> getuserbyid(HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			User user = userService.getUserById(id);

			return new ResponseEntity<User>(user, HttpStatus.OK);

		} else {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "setcollaborator", method = RequestMethod.POST)
	public ResponseEntity<String> setCollaborator(@RequestBody Note note, HttpServletRequest request) {
		String token = (String) request.getHeader("token");
		String email = (String) request.getHeader("email");
		logger.info(email);
		logger.info(note.getId());
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			User user = userService.getUserByEmail(email);
			noteService.addCollaborator(note.getId(), user);
            noteService.setCollabnotes(id, note);
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "getcollaborator", method = RequestMethod.POST)
	public ResponseEntity<List<User>> getCollaborator(@RequestBody Note note, HttpServletRequest request) {
		logger.info("note entered");
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			logger.info(note.getId()+note.getTitle());
			return new ResponseEntity<List<User>>(noteService.getCollaborator(note.getId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<User>>(HttpStatus.CONFLICT);
		}
	}
	

	@RequestMapping(value = "getcollabnotes", method = RequestMethod.POST)
	public ResponseEntity<List<Note>> getCollabnotes(@RequestBody User user, HttpServletRequest request) {
		logger.info("note entered");
		String token = (String) request.getHeader("token");
		if (userService.checkToken(token)) {
			int id = userService.getidbyToken(token);
			logger.info(user.getId());
			return new ResponseEntity<List<Note>>(noteService.getCollabnotes(user.getId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Note>>(HttpStatus.CONFLICT);
		}
	}

}
