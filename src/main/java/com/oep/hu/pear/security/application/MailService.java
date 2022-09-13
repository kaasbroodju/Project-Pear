package com.oep.hu.pear.security.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

	private static final String FRONTEND_REGISTRATION_LOCATION = "www.project-pear.com/register/";
	private JavaMailSender mailSender;


	public void sendRegistrationInvite(String mail, String message) {
//		try {
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(mail);
//
//			msg.setSubject("Complete your account with Pear");
//			msg.setText(message);
//
////			msg.setText("Complete your registration at: " + FRONTEND_REGISTRATION_LOCATION + inviteId);
//
//			this.mailSender.send(msg);
//		} catch (MailException e) {
//			log.warn("Failed to send email to: " + mail, e);
//		}
	}
}
