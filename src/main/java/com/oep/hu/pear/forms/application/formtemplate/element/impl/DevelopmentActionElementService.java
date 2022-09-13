package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentActionElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

@Service
public class DevelopmentActionElementService extends ElementService<DevelopmentActionElement> {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void setInnerValue(Map<String, Object> dto, DevelopmentActionElement element) {

		if (dto.get(element.getName() + "Date") != null && dto.get(element.getName() + "Date") != "") {
			element.setDate(LocalDate.parse((String) dto.get(element.getName() + "Date")));
		} else {
			element.setDate(null);
		}

		if (dto.get(element.getName() + "Status") != null) {
			element.setActionStatus(DevelopmentStatus.valueOf((String) dto.get(element.getName() + "Status")));
		}

		String action = (String) dto.get(element.getName() + "Action");
		element.setAction(action);

		String studentReflection = (String) dto.get(element.getName() + "StudentReflection");
		element.setStudentReflection(studentReflection);

		String teacherFeedback = (String) dto.get(element.getName() + "TeacherFeedback");
		element.setTeacherFeedback(teacherFeedback);
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, DevelopmentActionElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, DevelopmentActionElement element) {
		if (element.getDate() != null) values.put(element.getName() + "Date", element.getDate());
		if (element.getActionStatus() != null) values.put(element.getName() + "Status", element.getActionStatus());

		values.put(element.getName() + "Action", element.getAction());
		values.put(element.getName() + "StudentReflection", element.getStudentReflection());
		values.put(element.getName() + "TeacherFeedback", element.getTeacherFeedback());


	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, DevelopmentActionElement element) {

	}

}
