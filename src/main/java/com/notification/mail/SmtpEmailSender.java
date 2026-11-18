package com.notification.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/** Real SMTP sender (app.mail.enabled=true, spring.mail.host must be set). */
@Component
@ConditionalOnProperty(name = "app.mail.enabled", havingValue = "true")
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

	private final JavaMailSender mailSender;

	@Value("${app.mail.from}")
	private String from;

	@Override
	public void send(String to, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		mailSender.send(msg);
	}
}
