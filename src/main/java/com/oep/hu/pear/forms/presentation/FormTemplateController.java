package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.forms.presentation.dto.ElementDTO;
import com.oep.hu.pear.forms.task.FormTemplateTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/formtemplate")
@AllArgsConstructor
public class FormTemplateController {

	private FormTemplateTask task;

	@Operation(summary = "Get template names user can create")
	@GetMapping
	public List<String> getAllowedTemplates(Authentication authentication) {
		return this.task.getAllowedTemplates(authentication.getName());
	}

	@Operation(summary = "Get layout of a certain template version", description = "Get layout of a certain template version, so it can be cached")
	@GetMapping("/{templateName}/{version}")
	public List<ElementDTO> getLayoutOfSpecificTemplateVersion(@PathVariable String templateName, @PathVariable String version) {
		return this.task.getLayout(templateName, Long.parseLong(version)).getTemplateElements().stream()
				.map(value -> new ElementDTO(value.getName(), value.getLabel(), value.getType().getSimpleName(), value.getValidator())).toList();
	}
}
