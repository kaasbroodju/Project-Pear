package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.textelement.ReviewableTextElement;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReviewableTextElementService extends ElementService<ReviewableTextElement> {
	@Override
	public void setInnerValue(Map<String, Object> dto, ReviewableTextElement element) {
		if (dto.get(element.getName()) == null) return;
		element.setText((String) dto.get(element.getName()));
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, ReviewableTextElement element) {
		if (dto.get(element.getName() + "Reviewer") == null) return;
		element.setReviewerText((String) dto.get(element.getName() + "Reviewer"));
	}

	@Override
	public void getInnerValue(Map<String, Object> values, ReviewableTextElement element) {
		values.put(element.getName(), element.getText());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, ReviewableTextElement element) {
		values.put(element.getName() + "Reviewer", element.getReviewerText());
	}

}
