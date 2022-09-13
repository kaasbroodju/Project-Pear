package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.assessmentmatrix.data.DataPointTypeRepository;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentGoalOnDataPointTypeElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class DevelopmentGoalOnDataPointTypeElementService extends ElementService<DevelopmentGoalOnDataPointTypeElement> {

	private DataPointTypeRepository dataPointTypeRepository;

	// developmentGoalDataPointType	"Juiste kennis ontwikkelen"
	// developmentGoalDescription	"hjgjg"
	// developmentGoalStatus	"IN_DEVELOPMENT"
	// progressdevelopmentGoalCurrent	"IN_ONTWIKKELING"
	// progressdevelopmentGoalTarget	"IN_ONTWIKKELING"

	@Override
	public void setInnerValue(Map<String, Object> dto, DevelopmentGoalOnDataPointTypeElement element) {
		String dataPointTypeId = (String) dto.get(element.getName() + "DataPointType");
		if (dataPointTypeRepository.existsById(dataPointTypeId)) {
			element.setDataPointType(this.dataPointTypeRepository.getReferenceById(dataPointTypeId));
		}

		String description = (String) dto.get(element.getName() + "Description");
		element.setDescription(description);

		if (dto.get("progress" + element.getName() + "Current") != null) {
			element.setCurrentProgress(Progress.valueOf((String) dto.get("progress" + element.getName() + "Current")));
		}

		if (dto.get("progress" + element.getName() + "Target") != null) {
			element.setTargetProgress(Progress.valueOf((String) dto.get("progress" + element.getName() + "Target")));
		}

		if (dto.get(element.getName() + "Status") != null) {
			element.setDevelopmentStatus(DevelopmentStatus.valueOf((String) dto.get(element.getName() + "Status")));
		}
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, DevelopmentGoalOnDataPointTypeElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, DevelopmentGoalOnDataPointTypeElement element) {
		if (element.getDataPointType() != null) values.put(element.getName() + "DataPointType", element.getDataPointType().getType());
		values.put(element.getName() + "Description", element.getDescription());
		if (element.getCurrentProgress() != null) values.put("progress" + element.getName() + "Current", element.getCurrentProgress());
		if (element.getTargetProgress() != null) values.put("progress" + element.getName() + "Target", element.getTargetProgress());
		if (element.getDevelopmentStatus() != null) values.put(element.getName() + "Status", element.getDevelopmentStatus());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, DevelopmentGoalOnDataPointTypeElement element) {

	}

}
