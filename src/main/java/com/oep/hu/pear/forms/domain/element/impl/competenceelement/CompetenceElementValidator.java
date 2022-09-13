package com.oep.hu.pear.forms.domain.element.impl.competenceelement;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;

import javax.persistence.Entity;

@Entity
public class CompetenceElementValidator extends FormElementValidator {
	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
		if (!(element instanceof CompetenceElement competenceElement)) throw new InvalidFormElementValue("Wrong instance type");
		if (competenceElement.getCompetence() == null) throw new InvalidFormElementValue("Competence is null");

	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
