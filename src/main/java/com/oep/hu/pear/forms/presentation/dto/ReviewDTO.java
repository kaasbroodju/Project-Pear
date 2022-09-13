package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;

public record ReviewDTO(String reviewerEmail, DegreeOfAssessment degreeOfAssessment) {
}
