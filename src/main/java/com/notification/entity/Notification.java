package com.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String recipientEmail;

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false, columnDefinition = "text")
	private String message;

	private String type;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationStatus status = NotificationStatus.PENDING;

	@Column(columnDefinition = "text")
	private String error;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime sentAt;

	@PrePersist
	private void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
