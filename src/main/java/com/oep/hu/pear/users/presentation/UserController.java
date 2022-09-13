package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private PersonService personService;

	@Operation(summary = "Get user's name")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping("/name")
	public String getMyName(Authentication authentication) {
		try {
			return this.personService.getPerson(authentication.getName()).getName();
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
