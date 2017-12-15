package com.bridgelabz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="User")
public class User {
	@Id
	private int id;

	public int getId() {
		return id;
	}

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public void setId(int id) {
		this.id = id;
	}

    @Column(name="name")
	private String fname;

	
    @Column(name="email")
	private String email;

    @Column(name="password")
	private String password;
	
    @Column(name="number")
	private String number;


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



}
