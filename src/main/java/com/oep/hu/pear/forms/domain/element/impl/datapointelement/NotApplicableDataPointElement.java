package com.oep.hu.pear.forms.domain.element.impl.datapointelement;

import com.oep.hu.pear.forms.domain.Form;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class NotApplicableDataPointElement extends DataPointElement {

	public NotApplicableDataPointElement(String name, Form form) {
		super(name, form);
	}
}
