package com.oep.hu.pear.forms.domain.element.impl.datapointelement;

import com.oep.hu.pear.forms.domain.Form;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class SelfPickDataPointTypeDataPointElement extends DataPointElement {

	public SelfPickDataPointTypeDataPointElement(String name, Form form) {
		super(name, form);
	}
}
