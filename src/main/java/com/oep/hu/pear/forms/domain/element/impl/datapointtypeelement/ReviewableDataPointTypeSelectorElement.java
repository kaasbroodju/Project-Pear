package com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ReviewableDataPointTypeSelectorElement extends FormElement {

	@ManyToOne
	private DataPointType studentDataPointType;

	@ManyToOne
	private DataPointType reviewerDataPointType;

	public ReviewableDataPointTypeSelectorElement(String name, Form form) {
		super(name, form);
	}
}
