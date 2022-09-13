package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ExternalPerson extends Person {
	public ExternalPerson(String email) {
		super(email);
	}

	@Override
	public DegreeOfAssessment[] ableToGiveTypesOfReviews() {
		return new DegreeOfAssessment[]{DegreeOfAssessment.EXTERN};
	}
}
