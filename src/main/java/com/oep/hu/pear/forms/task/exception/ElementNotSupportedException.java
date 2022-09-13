package com.oep.hu.pear.forms.task.exception;

import com.oep.hu.pear.forms.domain.element.FormElement;

public class ElementNotSupportedException extends Exception {
	public ElementNotSupportedException(FormElement element) {
		super(element.getClass().getSimpleName() + " is not supported");
	}
}
