package com.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notification.dto.NotificationRequest;
import com.notification.dto.NotificationResponse;
import com.notification.dto.PageResponse;
import com.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('NOTIFICATION_SEND')")
	public NotificationResponse send(@Valid @RequestBody NotificationRequest request) {
		return service.send(request);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('NOTIFICATION_READ')")
	public PageResponse<NotificationResponse> list(@RequestParam(required = false) String recipient,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		return service.list(recipient, page, size);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('NOTIFICATION_READ')")
	public NotificationResponse get(@PathVariable Long id) {
		return service.get(id);
	}
}
