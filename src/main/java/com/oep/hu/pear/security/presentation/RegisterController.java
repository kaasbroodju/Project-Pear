package com.oep.hu.pear.security.presentation;

import com.oep.hu.pear.security.application.exception.AttemptAlreadyExistsException;
import com.oep.hu.pear.security.domain.RegisterRole;
import com.oep.hu.pear.security.presentation.dto.PasswordDTO;
import com.oep.hu.pear.security.task.RegisterTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

	private RegisterTask task;

	@Operation(summary = "Send email with register link to complete registration")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "208", description = "User is already invited, try later again"),
			@ApiResponse(responseCode = "400", description = "Invalid email format")
	})
	@PostMapping("/{email}")
	public void reserveRegisterId(@PathVariable String email) {
		try {
			this.task.createRegisterAttempt(email, RegisterRole.STUDENT);
		} catch (AttemptAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
		} catch (IllegalArgumentException iae) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Complete registration with Project Pear")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Register id not found or password too short or person already exist")
	})
	@PostMapping("/finish/{id}")
	public void CompleteRegistration(@PathVariable UUID id, @RequestBody PasswordDTO dto) {
		try {
			this.task.completeRegisterAttempt(id, dto.password());
		} catch (EntityNotFoundException | IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
