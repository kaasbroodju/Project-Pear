package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.domain.Badge;
import com.oep.hu.pear.users.task.BadgeTask;
import com.oep.hu.pear.users.task.exception.RestrictedBadgeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/badge")
@AllArgsConstructor
public class BadgeController {

	private BadgeTask badgeTask;

	@Operation(summary = "Gets user's own badges")
	@GetMapping
	public List<String> myBadges(Authentication authentication) {
		return this.badgeTask.getPersonsBadges(authentication.getName()).stream().map(Badge::getName).toList();
	}

	@Operation(summary = "Get badges of specific user")
	@GetMapping("{email}")
	public List<String> getPersonsBadges(@PathVariable String email) {
		return this.badgeTask.getPersonsBadges(email).stream().map(Badge::getName).toList();
	}

	@Operation(summary = "Get all badges with their group", description = "Get all badges and their group a user can assign to themselves manually.")
	@GetMapping("/group")
	public Map<String, List<String>> getBadgesGroupedByGroup() {
		Map<String, List<String>> output = new HashMap<>();
		this.badgeTask.getBadgesGroupedByGroup().forEach((key, value) -> output.put(key, value.stream().map(Badge::getName).toList()));

		// badges with the group null are only for admins
		output.remove(null);
		return output;
	}

	@Operation(summary = "Add badge to user's badges")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "403", description = "Badge is restricted and can only be assigned by an admin"),
			@ApiResponse(responseCode = "404", description = "Badge not found"),
	})

	@DeleteMapping("/user/{badgeName}")
	public void deleteBadgeFromUser(Authentication authentication, @PathVariable String badgeName) {
		try {
			this.badgeTask.removeBadgeFromUser(authentication.getName(), badgeName, false);
		} catch (RestrictedBadgeException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "remove badge from user's badges")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "403", description = "Badge is restricted and can only be assigned by an admin"),
			@ApiResponse(responseCode = "404", description = "Badge not found"),
	})
	@PostMapping("/user/{badgeName}")
	public void addBadgeToUser(Authentication authentication, @PathVariable String badgeName) {
		try {
			this.badgeTask.addBadgeToUser(authentication.getName(), badgeName, false);
		} catch (RestrictedBadgeException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
