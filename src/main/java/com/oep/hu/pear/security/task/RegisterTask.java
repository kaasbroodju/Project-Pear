package com.oep.hu.pear.security.task;

import com.oep.hu.pear.security.application.MailService;
import com.oep.hu.pear.security.application.RegisterAttemptService;
import com.oep.hu.pear.security.application.UserDetailsServiceImpl;
import com.oep.hu.pear.security.application.exception.AttemptAlreadyExistsException;
import com.oep.hu.pear.security.domain.RegisterAttempt;
import com.oep.hu.pear.security.domain.RegisterRole;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegisterTask {

	private MailService mailService;
	private RegisterAttemptService service;
	private UserDetailsServiceImpl userDetailsService;
	private StudentService studentService;
	private PersonService personService;

	public UUID createRegisterAttempt(String email, RegisterRole role) throws AttemptAlreadyExistsException, IllegalArgumentException {
		if (role != RegisterRole.EXTERN && !Pattern.matches("[a-z]+\\.[a-z]+@(?:hu.nl|student.hu.nl)", email)) throw new IllegalArgumentException("invalid mail");
		RegisterAttempt registerAttempt = this.service.createRegisterAttempt(email, role);

		mailService.sendRegistrationInvite(email, role.getFormattedMessage(registerAttempt.getId().toString()));
		return registerAttempt.getId();

	}

	public void completeRegisterAttempt(UUID attemptId, String password) throws EntityNotFoundException, IllegalArgumentException {
		if (password.length() < 8) throw new IllegalArgumentException("password too short");

		RegisterAttempt attempt = this.service.getEmailById(attemptId);
		String email = attempt.getMail();
		if (this.personService.doesPersonExistsByEmail(email)) throw new IllegalArgumentException("Person is already in the system.");
		if (attempt.getRole() == RegisterRole.TEACHER) {
			this.userDetailsService.createTeacher(email, password);
		} else {
			this.userDetailsService.createUser(email, password);
		}
		this.personService.savePerson(createPerson(attempt.getRole(), email));
	}

	private Person createPerson(RegisterRole role, String email) {
		return switch (role) {
			case APPRENTICE -> new Apprentice(email);
			case TEACHER-> new Teacher(email);
			case EXTERN -> new ExternalPerson(email);
			case STUDENT -> new Student(email);
		};
	}
}