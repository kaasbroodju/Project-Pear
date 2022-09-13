package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.presentation.dto.formOverviewDTO;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/old")
@AllArgsConstructor
public class OldController {

	private StudentService studentService;

	@Operation(summary = "Get forms marked done in current book")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
	})
	@GetMapping
	public List<formOverviewDTO> getCompletedForms(Authentication authentication) {
		try {
			return this.studentService.getStudentByEmail(authentication.getName())
					.getCurrentBook().getDones().stream()
					.map(form -> new formOverviewDTO(
							form.getId(),
							form.getTemplateName(),
							form.getName(),
							form.getFinishedAt()))
					.toList();
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping
//	public List<formOverviewDTO> getCompletedForms(Authentication authentication, Instant afterInstant) {
//		try {
//			return this.studentService.getStudentByEmail(authentication.getName()).getCurrentBook().getDones().stream().map(form -> new formOverviewDTO(form.getId(), form.getTemplateName(), form.getName(), form.getFinishedAt())).toList());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
}
