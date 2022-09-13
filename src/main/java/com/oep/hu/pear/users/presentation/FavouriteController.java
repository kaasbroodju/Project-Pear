package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import com.oep.hu.pear.users.task.FavouriteTask;
import com.oep.hu.pear.users.task.exception.NoSelfFavouriteException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favourite")
@AllArgsConstructor
public class FavouriteController {

	private FavouriteTask task;

	@Operation(summary = "Get list of all favourites of user")
	@GetMapping
	public Set<PersonDTO> myFavourites(Authentication authentication) {
		return this.task.getFavouritesEmails(authentication.getName()).stream().map(person -> new PersonDTO(person.getEmail(), person.getName())).collect(Collectors.toSet());
	}

	@Operation(summary = "Add other user to user's favourites")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "User can't favourite themselves"),
			@ApiResponse(responseCode = "404", description = "User does not exist")
	})
	@PostMapping("/{email}")
	public void addToFavourite(Authentication authentication, @PathVariable String email) {
		try {
			this.task.addToFavourites(authentication.getName(), email);
		} catch (NoSelfFavouriteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Remove other user to user's favourites")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "User does not exist")
	})
	@DeleteMapping("/{email}")
	public void removeFromFavourite(Authentication authentication, @PathVariable String email) {
		try {
			this.task.removeFromFavourites(authentication.getName(), email);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
