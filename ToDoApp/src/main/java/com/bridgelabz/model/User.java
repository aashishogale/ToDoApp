package com.bridgelabz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	/* @NotNull */
	private int id;

	@Column(name = "isVerified")
	private boolean isVerified;
	/* @NotNull */
	@Column(name = "name")
	private String fname;

	/* @NotNull */
	@Email
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
	@JsonIgnore 
	public Collection<Note> getCollabNotes() {
		return CollabNotes;
	}

	public void setCollabNotes(Collection<Note> collabNotes) {
		CollabNotes = collabNotes;
	}

	@Column(name = "number")
	private String number;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "colusernote",
	joinColumns = {
	@JoinColumn(name="usercolId") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="notecolId")
	}
	)

	
	
	private Collection<Note> CollabNotes=new ArrayList<Note>();

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
