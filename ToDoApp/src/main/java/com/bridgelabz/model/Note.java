package com.bridgelabz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Note")
public class Note implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7825199198283216229L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "noteid")
	private int id;

	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "date")
	private Date date;
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "colusernote",
	joinColumns = {
	@JoinColumn(name="notecolId") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="usercolId")
	}
	)


	private Collection<User> Collaborator = new ArrayList<User>();
	@JsonIgnore 
	public Collection<User> getCollaborator() {
		return Collaborator;
	}

	public void setCollaborator(Collection<User> collaborator) {
		Collaborator = collaborator;
	}


	@Column(name = "pin")
	private boolean pin;

	@Column(name = "archive")
	private boolean archive;
	@Column(name = "trash")
	private boolean trash;
	@Column(name = "reminder")
	private Date reminder;

	@Column(name = "color")
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
