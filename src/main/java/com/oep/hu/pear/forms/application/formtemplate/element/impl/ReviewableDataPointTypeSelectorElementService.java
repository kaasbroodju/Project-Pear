package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.assessmentmatrix.data.DataPointTypeRepository;
import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.ReviewableDataPointTypeSelectorElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ReviewableDataPointTypeSelectorElementService extends ElementService<ReviewableDataPointTypeSelectorElement> {

	private DataPointTypeRepository dataPointTypeRepository;

	@Override
	public void setInnerValue(Map<String, Object> dto, ReviewableDataPointTypeSelectorElement element) {
		String dataPointTypeId = (String) dto.get(element.getName() + "DataPointType");
		if (dataPointTypeId == null) {
			element.setStudentDataPointType(null);
			return;
		}
		if (!dataPointTypeRepository.existsById(dataPointTypeId)) {
			throw new IllegalArgumentException("DataPointType " + dataPointTypeId + " does not exists");
		}
		element.setStudentDataPointType(dataPointTypeRepository.getReferenceById(dataPointTypeId));

	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, ReviewableDataPointTypeSelectorElement element) {
		String dataPointTypeId = (String) dto.get("reviewer" + element.getName() + "DataPointType");
		if (dataPointTypeId == null) {
			element.setReviewerDataPointType(null);
			return;
		}
		if (!dataPointTypeRepository.existsById(dataPointTypeId)) {
			throw new IllegalArgumentException("DataPointType " + dataPointTypeId + " does not exists");
		}
		element.setReviewerDataPointType(dataPointTypeRepository.getReferenceById(dataPointTypeId));
	}

	@Override
	public void getInnerValue(Map<String, Object> values, ReviewableDataPointTypeSelectorElement element) {
		if (element.getStudentDataPointType() != null) values.put(element.getName() + "DataPointType", element.getStudentDataPointType().getType());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, ReviewableDataPointTypeSelectorElement element) {
		if (element.getReviewerDataPointType() != null) values.put("reviewer" + element.getName() + "DataPointType", element.getReviewerDataPointType().getType());
	}

}
