package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.TeacherService;
import com.oep.hu.pear.users.presentation.dto.ApprenticeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class TeacherController {

	private TeacherService service;

	@Operation(summary = "Get list of teachers")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping
	public List<ApprenticeDTO> getAllTeachers() {
		return this.service.getAllTeachers().stream()
				.map(teacher -> new ApprenticeDTO(
						teacher.getEmail(),
						teacher.getName(),
						teacher.getMyGuild() == null ? "" : teacher.getMyGuild().getName()))
				.toList();
	}
}
