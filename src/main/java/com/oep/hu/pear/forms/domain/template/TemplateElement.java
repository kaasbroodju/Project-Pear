package com.oep.hu.pear.forms.domain.template;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.FormElementValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(TemplateElementId.class)
public class TemplateElement {

	@Id
	@ManyToOne
	private FormTemplateLayout templateVersion;
	@Id
	private String name;
	@Id
	private int position;

	private String label;

	private Class<? extends FormElement> type;

	@OneToOne(cascade = CascadeType.ALL)
	private FormElementValidator validator;

	public TemplateElement(String name, Class<? extends FormElement> type, String label, FormElementValidator validator) {
		this.name = name;
		this.label = label;
		this.type = type;
		this.validator = validator;
	}
}
