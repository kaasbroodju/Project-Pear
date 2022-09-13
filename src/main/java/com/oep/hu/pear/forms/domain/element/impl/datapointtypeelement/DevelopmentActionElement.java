package com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DevelopmentActionElement extends FormElement {

	private LocalDate date;

	private DevelopmentStatus actionStatus;

	private String action;

	private String studentReflection;

	private String teacherFeedback;

	public DevelopmentActionElement(String name, Form form) {
		super(name, form);
	}
}
