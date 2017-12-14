package com.bridgelabz.controller;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailSetter {

	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("website");
		message.setTo(to);
		message.setSubject("welcome mail");
		message.setText("you have been verified");
		mailSender.send(message);
		System.out.println("mail sent");
	}

	public void sendOtp(String to, int otp) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("website");
		message.setTo(to);
		message.setSubject("Your otp");
		message.setText(Integer.toString(otp));
		mailSender.send(message);
	}

}
