package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public final class OpenReviewDao implements Serializable {
	private final Long id;
	private final String title;
	private final String description;
	private final DegreeOfAssessment wantedDegreeOfAssessment;
	private final String mainCompetence;
	private final String subCompetence;
	private final Short level;

	public OpenReviewDao(Long id, String title, String description, DegreeOfAssessment wantedDegreeOfAssessment, String mainCompetence, String subCompetence, Short level) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.wantedDegreeOfAssessment = wantedDegreeOfAssessment;
		this.mainCompetence = mainCompetence;
		this.subCompetence = subCompetence;
		this.level = level;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OpenReviewDao)) return false;
		OpenReviewDao that = (OpenReviewDao) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
