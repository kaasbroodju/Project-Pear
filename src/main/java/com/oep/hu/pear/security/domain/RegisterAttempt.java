package com.oep.hu.pear.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class RegisterAttempt {
	@Id
	@GeneratedValue
	private UUID id;
	private String mail;
	private RegisterRole role;
	private Instant createdAt;

	public RegisterAttempt(String mail, RegisterRole role) {
		this.mail = mail;
		this.createdAt = Instant.now();
		this.role = role;
	}
}
