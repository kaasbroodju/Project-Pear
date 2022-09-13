package com.oep.hu.pear.users.presentation.dto;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;

import java.time.Instant;

public record ProgressCriteriaDTO(java.util.UUID id, String name, Progress progress, DegreeOfAssessment degreeOfAssessment, Instant time) {
}
