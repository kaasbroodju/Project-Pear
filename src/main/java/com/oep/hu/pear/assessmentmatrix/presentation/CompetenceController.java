package com.oep.hu.pear.assessmentmatrix.presentation;

import com.oep.hu.pear.assessmentmatrix.application.CompetenceService;
import com.oep.hu.pear.assessmentmatrix.presentation.dto.CompetenceDTO;
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
@RequestMapping("/competence")
@AllArgsConstructor
public class CompetenceController {

	private CompetenceService service;


	@Operation(summary = "Get list of all competences")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping
	public List<CompetenceDTO> getAll() {
		return this.service.getAll().stream()
				.map(competence -> new CompetenceDTO(
						competence.getMainCompetence(),
						competence.getSubCompetence(),
						competence.getLevel(),
						competence.getDescription()))
				.toList();
	}
}
