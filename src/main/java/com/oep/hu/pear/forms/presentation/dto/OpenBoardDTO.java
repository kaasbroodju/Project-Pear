package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.assessmentmatrix.presentation.dto.CompetenceNoDescDTO;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;

import java.util.UUID;

public record OpenBoardDTO(UUID id, String title, String templateName, DegreeOfAssessment preferredReviewer, CompetenceNoDescDTO competence, String name) {
}
