package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.assessmentmatrix.data.DataPointTypeRepository;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.ReviewerEvaluation;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.StudentEvaluation;
import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.SelfPickDataPointTypeDataPointElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class SelfPickDataPointTypeDataPointElementService extends ElementService<SelfPickDataPointTypeDataPointElement> {

	private DataPointTypeRepository dataPointTypeRepository;

	@Override
	public void setInnerValue(Map<String, Object> dto, SelfPickDataPointTypeDataPointElement element) {
		String dataPointTypeId = (String) dto.get(element.getName() + "DataPointType");
		if (dataPointTypeId == null) {
			element.setDataPointType(null);
			element.setStudentEvaluation(new StudentEvaluation());
			return;
		}
		if (!dataPointTypeRepository.existsById(dataPointTypeId)) {
			throw new IllegalArgumentException("DataPointType " + dataPointTypeId + " does not exists");
		}
		element.setDataPointType(dataPointTypeRepository.getReferenceById(dataPointTypeId));


		String studentProof = "";
		Progress studentProgress = null;
		if (dto.get(element.getName()) != null) {
			studentProof = (String) dto.get(element.getName());
		}
		if (dto.get("progress" + element.getName()) != null) {
			studentProgress = Progress.valueOf((String) dto.get("progress" + element.getName()));
		}

		element.setStudentEvaluation(new StudentEvaluation(studentProgress, studentProof));
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, SelfPickDataPointTypeDataPointElement element) {
		String reviewerCompliment = "";
		String reviewerImprovement = "";
		Progress reviewerProgress = null;

		if (dto.get(element.getName() + "ComplimentReviewer") != null) {
			reviewerCompliment = (String) dto.get(element.getName() + "ComplimentReviewer");
		}
		if (dto.get(element.getName() + "ImprovementReviewer") != null) {
			reviewerImprovement = (String) dto.get(element.getName() + "ImprovementReviewer");
		}
		if (dto.get("progress" + element.getName() + "Reviewer") != null) {
			reviewerProgress = Progress.valueOf((String) dto.get("progress" + element.getName() + "Reviewer"));
		}
		element.setReviewerEvaluation(new ReviewerEvaluation(reviewerProgress, reviewerCompliment, reviewerImprovement));
	}

	@Override
	public void getInnerValue(Map<String, Object> values, SelfPickDataPointTypeDataPointElement element) {
		if (element.getDataPointType() != null) {
			values.put(element.getName() + "DataPointType", element.getDataPointType().getType());
			values.put("progress" + element.getName(), element.getStudentEvaluation().getProgress());
			values.put(element.getName(), element.getStudentEvaluation().getProof());
		} else {
			values.put(element.getName() + "DataPointType", "undefined");
		}
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, SelfPickDataPointTypeDataPointElement element) {
		if (element.getDataPointType() != null) {
			values.put("progress" + element.getName() + "Reviewer", element.getReviewerEvaluation().getProgress());
			values.put(element.getName() + "ImprovementReviewer", element.getReviewerEvaluation().getFeedback());
			values.put(element.getName() + "ComplimentReviewer", element.getReviewerEvaluation().getCompliment());
		}
	}

}
