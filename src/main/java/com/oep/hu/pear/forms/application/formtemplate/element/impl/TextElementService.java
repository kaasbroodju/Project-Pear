package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.textelement.TextElement;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TextElementService extends ElementService<TextElement> {
	@Override
	public void setInnerValue(Map<String, Object> dto, TextElement element) {
		if (dto.get(element.getName()) == null) return;
		element.setText((String) dto.get(element.getName()));
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, TextElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, TextElement element) {
		values.put(element.getName(), element.getText());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, TextElement element) {

	}

}
