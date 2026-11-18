package com.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

	@NotBlank
	@Email
	private String recipientEmail;

	@NotBlank
	@Size(max = 200)
	private String subject;

	@NotBlank
	@Size(max = 10000)
	private String message;

	@Size(max = 50)
	private String type;
}
