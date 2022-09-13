package com.oep.hu.pear.assessmentmatrix.data;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;

import java.time.Instant;

public record ProgressDataPointTypeDAO(long id, String name, Progress progress, DegreeOfAssessment degreeOfAssessment, Instant time) {
}
