package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.presentation.dto.FormTemplateVersionIdDTO;
import com.oep.hu.pear.forms.presentation.dto.RefactorFormMetaDTO;
import com.oep.hu.pear.forms.task.FormTemplateTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher/formtemplate")
@AllArgsConstructor
public class TeacherFormTemplateController {

	private FormTemplateTask task;

	@Operation(summary = "Get all template names")
	@GetMapping
	public List<String> getTemplates() {
		return this.task.getAllTemplates();
	}

	@Operation(summary = "Get meta of the most recent forms using the speciified template of students in the speciified group")
	@GetMapping("/{groupname}/{templateName}")
	public Map<String, RefactorFormMetaDTO> getGroupsLatestMetaOnForm(@PathVariable String groupname, @PathVariable String templateName) {
		return this.task.getRecentFromTemplatesOfGroup(groupname, templateName).entrySet()
			.stream()
			.collect(
				Collectors.toMap(
					Map.Entry::getKey,
					e -> new RefactorFormMetaDTO(
						e.getValue().getId(),
						e.getValue().getName(),
						e.getValue().getFormStatus(),
						e.getValue().getCreatedAt(),
						e.getValue().getFinishedAt(),
						e.getValue().getReviewPolicy(),
						new FormTemplateVersionIdDTO(
							e.getValue().getTemplateName(),
							e.getValue().getTemplateVersion()))));
	}
}
