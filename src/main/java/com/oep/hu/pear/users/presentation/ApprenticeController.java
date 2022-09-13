package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.ApprenticeService;
import com.oep.hu.pear.users.presentation.dto.ApprenticeDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apprentice")
@AllArgsConstructor
public class ApprenticeController {

	private ApprenticeService apprenticeService;

	@Operation(summary = "Get all apprentices", description = "Get all apprentices, filters out user self")
	@GetMapping
	public List<ApprenticeDTO> getAll(Authentication authentication) {
		return this.apprenticeService.getAll().stream().filter(apprentice -> !authentication.getName().equals(apprentice.getEmail())).map(
				apprentice -> new ApprenticeDTO(apprentice.getEmail(), apprentice.getName(), apprentice.getMyGuild() == null ? "" : apprentice.getMyGuild().getName())
		).toList();
	}
}
