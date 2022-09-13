package com.oep.hu.pear.forms.domain.element.impl.textelement;

import com.oep.hu.pear.forms.domain.Form;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ReviewableTextElement extends TextElement {

	@Column(columnDefinition = "TEXT")
	private String reviewerText = "";

	public ReviewableTextElement(String name, Form form) {
		super(name, form);
	}
}
