package com.oep.hu.pear.forms.application.formtemplate.element;

import com.oep.hu.pear.forms.domain.element.FormElement;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class ElementService<T extends FormElement> {

	public abstract void setInnerValue(Map<String, Object> dto, T element);

	public abstract void setInnerValueReviewer(Map<String, Object> dto, T element);

	public abstract void getInnerValue(Map<String, Object> values, T element);

	public abstract void getInnerValueReviewer(Map<String, Object> values, T element);

}
