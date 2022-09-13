package com.oep.hu.pear.security.application;


import com.oep.hu.pear.security.application.exception.AttemptAlreadyExistsException;
import com.oep.hu.pear.security.data.RegisterAttemptRepository;
import com.oep.hu.pear.security.domain.RegisterAttempt;
import com.oep.hu.pear.security.domain.RegisterRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterAttemptService {

	private RegisterAttemptRepository repository;

	public RegisterAttempt createRegisterAttempt(String mail, RegisterRole role) throws AttemptAlreadyExistsException {
		if (this.repository.existsByMailAndCreatedAtIsBefore(mail, Instant.now().minus(role.getAvailableTimeInvite(), ChronoUnit.MINUTES))) throw new AttemptAlreadyExistsException();
		return this.repository.save(new RegisterAttempt(mail, role));
	}

	public RegisterAttempt getEmailById(UUID id) throws EntityNotFoundException {
		return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
