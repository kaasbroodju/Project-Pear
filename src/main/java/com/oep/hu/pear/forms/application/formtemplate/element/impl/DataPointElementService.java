package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.ReviewerEvaluation;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.StudentEvaluation;
import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataPointElementService extends ElementService<DataPointElement> {
	@Override
	public void setInnerValue(Map<String, Object> dto, DataPointElement element) {
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
	public void setInnerValueReviewer(Map<String, Object> dto, DataPointElement element) {
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
	public void getInnerValue(Map<String, Object> values, DataPointElement element) {
		values.put(element.getName(), element.getStudentEvaluation().getProof());
		values.put("progress" + element.getName(), element.getStudentEvaluation().getProgress());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, DataPointElement element) {
		values.put(element.getName() + "ComplimentReviewer", element.getReviewerEvaluation().getCompliment());
		values.put(element.getName() + "ImprovementReviewer", element.getReviewerEvaluation().getFeedback());
		values.put("progress" + element.getName() + "Reviewer", element.getReviewerEvaluation().getProgress());
	}

}
