package com.oep.hu.pear.forms.domain.element.impl.assessmentelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.StudentEvaluation;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AssessmentElement extends FormElement {

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "progress", column = @Column(name = "progress_student"))
	})
	private StudentEvaluation studentEvaluation = new StudentEvaluation();


	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "progress", column = @Column(name = "progress_teacher")),
			@AttributeOverride(name = "proof", column = @Column(name = "exegesis"))
	})
	private StudentEvaluation TeacherEvaluation = new StudentEvaluation();


	public AssessmentElement(String name, Form form) {
		super(name, form);
	}
}
