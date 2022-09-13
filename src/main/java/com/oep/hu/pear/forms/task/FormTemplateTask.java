package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.forms.application.formtemplate.FormTemplateService;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayout;
import com.oep.hu.pear.users.application.GroupService;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FormTemplateTask {

	private FormTemplateService service;
	private PersonService personService;
	private GroupService groupService;

	public List<String> getAllowedTemplates(String user) {
		// todo try query with Type()
		return this.service.getAllowedTemplates(Hibernate.getClass(this.personService.getPerson(user)));
	}

	public List<String> getAllTemplates() {
		return this.service.getAllTemplateNames();
	}

	public FormTemplateLayout getLayout(String templateName, long version) {
		return this.service.getFormLayout(templateName, version);
	}

	public Map<String, Form> getRecentFromTemplatesOfGroup(String groupName, String templateName) {
		Map<String, Form> output = new HashMap<>();
		Group group = this.groupService.getGroup(groupName);
		for (Student student : group.getStudents()) {
			student.getCurrentBook().getDones().stream()
					.filter(form -> form.getTemplateName().equals(templateName))
					.min(Comparator.comparing(Form::getFinishedAt))
					.ifPresent(form -> output.put(student.getName(), form));
		}
		return output;
	}
}
