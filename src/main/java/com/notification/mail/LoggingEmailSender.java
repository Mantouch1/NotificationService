package com.notification.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** Default sender (app.mail.enabled=false): does not send anything, only logs. Perfect for development. */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.mail.enabled", havingValue = "false", matchIfMissing = true)
public class LoggingEmailSender implements EmailSender {

	@Override
	public void send(String to, String subject, String body) {
		log.info("[MAIL DISABLED] would send to={} subject='{}'", to, subject);
	}
}
