package com.oep.hu.pear.users.task;

import com.oep.hu.pear.assessmentmatrix.application.DataPointTypeService;
import com.oep.hu.pear.assessmentmatrix.application.ProgressService;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.forms.application.formtemplate.element.impl.DurationElementService;
import com.oep.hu.pear.forms.application.formtemplate.element.impl.EmotionElementService;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElement;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElement;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProgressTask {

	private ProgressService progressService;
	private StudentService studentService;
	private DataPointTypeService dataPointTypeService;
	private DurationElementService durationElementService;
	private EmotionElementService emotionElementService;

	public List<DataPointElement> dataPointListOfCurrentBookOfStudent(String studentEmail, String dataPointTypeName) {
		DataPointType dataPointType = dataPointTypeService.getDataPointType(dataPointTypeName);
		Student student = this.studentService.getStudentByEmail(studentEmail);
		return this.progressService.getStudentDataPointsFromCurrentBook(dataPointType, student.getCurrentBook().getDones());
	}

	public List<DurationElement> durationListOfCurrentBookOfStudent(String studentEmail) {
		Student student = this.studentService.getStudentByEmail(studentEmail);
		return this.durationElementService.getDurationPoints(student.getCurrentBook().getDones());
	}

	public List<EmotionElement> emotionListOfCurrentBookOfStudent(String studentEmail) {
		Student student = this.studentService.getStudentByEmail(studentEmail);
		return this.emotionElementService.getEmotionPoints(student.getCurrentBook().getDones());
	}

	public List<DataPointElement> dataPointListOfCurrentBookOfStudent(String name, String studentEmail, String dataPointTypeName) {
		DataPointType dataPointType = dataPointTypeService.getDataPointType(dataPointTypeName);
		Student student = this.studentService.getStudentByEmail(studentEmail);
		return this.progressService.getStudentDataPointsFromCurrentBook(dataPointType, student.getCurrentBook().getDones());

	}
}
