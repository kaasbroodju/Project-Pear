package com.oep.hu.pear.forms.domain.element.impl.textelement;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;

import javax.persistence.Entity;

@Entity
public class TextElementValidator extends FormElementValidator {


	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
		if (!(element instanceof TextElement textElement)) throw new InvalidFormElementValue("Wrong instance type");
		if (textElement.getText().isBlank()) throw new InvalidFormElementValue("Text is blank");
	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
