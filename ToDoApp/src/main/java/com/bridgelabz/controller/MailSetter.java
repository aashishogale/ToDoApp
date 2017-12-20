package com.bridgelabz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailSetter {

	@Autowired
	private MailSender mailSender;
	
	

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Async
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
	@Async
	public void sendUrl(String url) {
		SimpleMailMessage message = new SimpleMailMessage();
		String to="aashishogale9@gmail.com";
		message.setFrom("website");
		message.setTo(to);
		message.setSubject("Click on the  link");
		message.setText(url);
		mailSender.send(message);
		
	}
	

}
