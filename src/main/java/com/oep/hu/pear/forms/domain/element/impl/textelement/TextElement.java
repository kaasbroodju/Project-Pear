package com.oep.hu.pear.forms.domain.element.impl.textelement;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TextElement extends FormElement {

	@Column(columnDefinition = "TEXT")
	private String text = "";

	public TextElement(String name, Form form) {
		super(name, form);
	}
}
