package com.oep.hu.pear.assessmentmatrix.presentation.dto;

public record CompetenceDTO(
		String mainCompetence,
		String subCompetence,
		short level,
		String description
) {
}
