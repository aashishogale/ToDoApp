package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	@RequestMapping(value = "createnote", method = RequestMethod.POST)
	public ResponseEntity<Note> createNote(@RequestBody Note note, HttpServletRequest request) {

		String token = (String) request.getHeader("token");
		
		int id = userService.checkToken(token);
		User user = userService.getUserById(id);
		noteService.createNote(user, note);
		return new ResponseEntity<Note>(HttpStatus.OK);

	}

	@RequestMapping(value = "updatenote", method = RequestMethod.POST)
	public ResponseEntity<Note> updateNote(@RequestBody Note note, HttpServletRequest request) {

		noteService.updateNote(note);
		return new ResponseEntity<Note>(note,HttpStatus.OK);

	}

	@RequestMapping(value = "deletenote", method = RequestMethod.POST)
	public ResponseEntity<Note> deleteNote(@RequestBody Note note, HttpServletRequest request) {

		noteService.deleteNote(note);
		return new ResponseEntity<Note>(note,HttpStatus.OK);

	}
	
	@RequestMapping(value="returnnotelist",method=RequestMethod.POST)
	public ResponseEntity<List<Note>> getAllNotes(@RequestBody User user){
		
		 return new ResponseEntity<List<Note>>(noteService.getNoteList(user),HttpStatus.OK);
		
	}

}
