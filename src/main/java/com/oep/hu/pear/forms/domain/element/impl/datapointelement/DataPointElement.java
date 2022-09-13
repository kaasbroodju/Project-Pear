package com.oep.hu.pear.forms.domain.element.impl.datapointelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.ReviewerEvaluation;
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
public class DataPointElement extends FormElement {

	@ManyToOne
	private DataPointType dataPointType;

	@Embedded @Setter
	@AttributeOverrides({
			@AttributeOverride(name = "progress", column = @Column(name = "progress_student"))
	})
	private StudentEvaluation studentEvaluation = new StudentEvaluation();

	@Embedded @Setter
	@AttributeOverrides({
			@AttributeOverride(name = "progress", column = @Column(name = "progress_reviewer"))
	})
	private ReviewerEvaluation reviewerEvaluation = new ReviewerEvaluation();


	public DataPointElement(String name, Form form) {
		super(name, form);
	}
}
