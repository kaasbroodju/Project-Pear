package com.oep.hu.pear.security.presentation;

import com.oep.hu.pear.security.application.exception.AttemptAlreadyExistsException;
import com.oep.hu.pear.security.presentation.dto.AdminRegisterDTO;
import com.oep.hu.pear.security.task.RegisterTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin/register")
@AllArgsConstructor
public class AdminRegisterController {

	private RegisterTask task;

	@Operation(summary = "Invite user with specified role")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "208", description = "User is already invited, try later again"),
			@ApiResponse(responseCode = "400", description = "Invalid email format")
	})
	@PostMapping
	public void reserveRegisterId(@RequestBody AdminRegisterDTO dto) {
		try {
			this.task.createRegisterAttempt(dto.email(), dto.role());
		} catch (AttemptAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
		} catch (IllegalArgumentException iae) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
