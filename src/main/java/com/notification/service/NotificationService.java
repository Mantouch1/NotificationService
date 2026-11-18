package com.notification.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.notification.dto.NotificationRequest;
import com.notification.dto.NotificationResponse;
import com.notification.dto.PageResponse;
import com.notification.entity.Notification;
import com.notification.entity.NotificationStatus;
import com.notification.exception.ResourceNotFoundException;
import com.notification.mail.EmailSender;
import com.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository repository;
	private final EmailSender emailSender;

	/** Stores the notification, then tries to send it; the result (SENT / FAILED) is stored too. */
	public NotificationResponse send(NotificationRequest request) {
		String subject = request.getSubject().replaceAll("[\\r\\n]+", " ").trim(); // no header injection
		Notification n = repository.save(Notification.builder()
				.recipientEmail(request.getRecipientEmail().trim())
				.subject(subject)
				.message(request.getMessage())
				.type(request.getType())
				.build());
		try {
			emailSender.send(n.getRecipientEmail(), n.getSubject(), n.getMessage());
			n.setStatus(NotificationStatus.SENT);
			n.setSentAt(LocalDateTime.now());
		} catch (Exception e) {
			log.warn("Notification {} could not be sent: {}", n.getId(), e.getMessage());
			n.setStatus(NotificationStatus.FAILED);
			String msg = String.valueOf(e.getMessage());
			n.setError(msg.length() > 1000 ? msg.substring(0, 1000) : msg);
		}
		return toResponse(repository.save(n));
	}

	public PageResponse<NotificationResponse> list(String recipient, int page, int size) {
		PageRequest pr = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<Notification> result = (recipient == null || recipient.isBlank())
				? repository.findAll(pr)
				: repository.findByRecipientEmailIgnoreCase(recipient.trim(), pr);
		return PageResponse.of(result.map(this::toResponse));
	}

	public NotificationResponse get(Long id) {
		return toResponse(repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id)));
	}

	private NotificationResponse toResponse(Notification n) {
		return NotificationResponse.builder().id(n.getId()).recipientEmail(n.getRecipientEmail()).subject(n.getSubject())
				.message(n.getMessage()).type(n.getType()).status(n.getStatus().name()).error(n.getError())
				.createdAt(n.getCreatedAt()).sentAt(n.getSentAt()).build();
	}
}
