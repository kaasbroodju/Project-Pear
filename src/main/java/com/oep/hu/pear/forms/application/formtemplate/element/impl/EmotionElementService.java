package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.data.EmotionElementRepository;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.Emotion;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmotionElementService extends ElementService<EmotionElement> {

	private EmotionElementRepository repository;

	public List<EmotionElement> getEmotionPoints(Set<Form> forms) {
		return this.repository.findByFormIsInAndForm_FinishedAtIsNotNull(forms);
	}

	@Override
	public void setInnerValue(Map<String, Object> dto, EmotionElement element) {
		if (dto.get(element.getName()) == null) return;
		element.setEmotion(Emotion.valueOf((String) dto.get(element.getName())));
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, EmotionElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, EmotionElement element) {
		values.put(element.getName(), element.getEmotion());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, EmotionElement element) {

	}

}
