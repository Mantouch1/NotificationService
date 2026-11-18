package com.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

	private Long id;
	private String recipientEmail;
	private String subject;
	private String message;
	private String type;
	private String status;
	private String error;
	private LocalDateTime createdAt;
	private LocalDateTime sentAt;
}
