package com.oep.hu.pear.forms.domain.element.impl.durationelement;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;

import javax.persistence.Entity;
import java.time.Duration;

@Entity
public class DurationElementValidator extends FormElementValidator {

	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
		if (!(element instanceof DurationElement durationElement)) throw new InvalidFormElementValue("Wrong instance type");
		if (Duration.ZERO.plusHours(24).minus(durationElement.getDuration()).isNegative()) throw new InvalidFormElementValue("Time duration is more then 24 hours");
	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
