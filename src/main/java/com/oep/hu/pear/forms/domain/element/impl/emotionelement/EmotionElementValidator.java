package com.oep.hu.pear.forms.domain.element.impl.emotionelement;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;

import javax.persistence.Entity;

@Entity
public class EmotionElementValidator extends FormElementValidator {

	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
		if (!(element instanceof EmotionElement emotionElement)) throw new InvalidFormElementValue("Wrong instance type");
		if (emotionElement.getEmotion() == null) throw new InvalidFormElementValue("No emotion is selected");
	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
