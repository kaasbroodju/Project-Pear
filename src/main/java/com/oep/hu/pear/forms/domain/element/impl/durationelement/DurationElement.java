package com.oep.hu.pear.forms.domain.element.impl.durationelement;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.Duration;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DurationElement extends FormElement {

	private Duration duration = Duration.ZERO;

	public DurationElement(String name, Form form) {
		super(name, form);
	}
}
