package com.oep.hu.pear.forms.domain.element.impl.emotionelement;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EmotionElement extends FormElement {

	private Emotion emotion;

	public EmotionElement(String name, Form form) {
		super(name, form);
	}
}
