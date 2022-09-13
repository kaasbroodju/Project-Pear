package com.oep.hu.pear.forms.domain.element.impl.datapointelement;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SelfPickDataPointTypeDataPointElementValidator extends DataPointElementValidator {

	@ManyToMany(fetch = FetchType.EAGER) @Getter
	private List<DataPointType> bannedTypes;

	@Override
	public void validateInnerValue(FormElement element) throws InvalidFormElementValue {
	}

	@Override
	public void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue {
	}
}
