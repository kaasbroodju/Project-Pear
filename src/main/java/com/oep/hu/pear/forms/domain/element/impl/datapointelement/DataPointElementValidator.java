package com.oep.hu.pear.forms.domain.element.impl.datapointelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.StudentEvaluation;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;

import javax.persistence.Entity;

@Entity
public class DataPointElementValidator extends FormElementValidator {
	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
		if (!(element instanceof DataPointElement dataPointElement)) throw new InvalidFormElementValue("Wrong instance type");
		StudentEvaluation studentEvaluation = dataPointElement.getStudentEvaluation();
		if (studentEvaluation.getProof().isBlank()) throw new InvalidFormElementValue("Proof is blank");
		if (studentEvaluation.getProgress() == null) throw new InvalidFormElementValue("No progress selected");
	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
