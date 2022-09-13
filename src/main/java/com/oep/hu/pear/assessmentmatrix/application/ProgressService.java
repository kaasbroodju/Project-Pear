package com.oep.hu.pear.assessmentmatrix.application;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.forms.data.DataPointElementRepository;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class ProgressService {

//	private CriteriaRepository repository;
	private DataPointElementRepository repository;

	public List<DataPointElement> getStudentDataPointsFromCurrentBook(DataPointType dataPointType,Collection<Form> forms) {
		return repository.findByFormInAndDataPointTypeOrderByForm_FinishedAtDesc(forms, dataPointType);
//		return this.repository.findByFormInAndForm_FormStatusEquals(forms, FormStatus.DONE);
	}
}
