package com.oep.hu.pear.forms.domain.element.impl.competenceelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
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
public class CompetenceElement extends FormElement {

	@ManyToOne
	private Competence competence;

	private Progress progress;

	private String explanation;

	public CompetenceElement(String name, Form form) {
		super(name, form);
	}
}
