package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.presentation.dto.FormTemplateVersionIdDTO;
import com.oep.hu.pear.forms.presentation.dto.RefactorFormMetaDTO;
import com.oep.hu.pear.forms.task.FormTask;
import com.oep.hu.pear.forms.task.exception.InvalidFormAccessException;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/form")
@AllArgsConstructor
public class FormController {

	private FormTask task;

	@Operation(summary = "Create new form with given template name")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User is not allowed to create form with given template")
	})
	@PostMapping("/create/{templateName}")
	public UUID create(Authentication authentication, @PathVariable String templateName) {
		try {
			return this.task.createForm(authentication.getName(), templateName);

		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (AccessDeniedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	private RefactorFormMetaDTO translateToMetaDTO(Form form) {
//		LinkedHashMap<String, ElementDTO> elementDTO = new LinkedHashMap<>();
//		form.getFormTemplate().getTemplateElements().forEach(value -> elementDTO.put(value.getName(), new ElementDTO(value.getName(), value.getLabel(), value.getType().getSimpleName(), value.getValidator())));
		return new RefactorFormMetaDTO(
				form.getId(),
				form.getName(),
				form.getFormStatus(),
				form.getCreatedAt(),
				form.getFinishedAt(),
				form.getReviewPolicy(),
				new FormTemplateVersionIdDTO(form.getTemplateName(), form.getTemplateVersion())
		);
	}

	@Operation(summary = "Get information about a form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401")
	})
	@GetMapping("/{id}/meta")
	public RefactorFormMetaDTO fetchMeta(Authentication authentication, @PathVariable UUID id) {
		try {
			return this.translateToMetaDTO(this.task.getForm(authentication.getName(), id));
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Change name of form instance", description = "Change form name so user can recognise form easier when in a list.")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Form not found"),
			@ApiResponse(responseCode = "401", description = "User is not the owner of form instance"),
	})
	@PatchMapping("/{id}/name/{value}")
	public void changeName(Authentication authentication, @PathVariable UUID id, @PathVariable String value) {
		try {
			this.task.changeName(authentication.getName(), id, value);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Get data of form", description = "Returns a flat array with key value pairs.")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form")
	})
	@GetMapping("/{id}")
	public Map<String, Object> fetchParams(Authentication authentication, @PathVariable UUID id) {
		try {
			return this.task.getFormValues(authentication.getName(), id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "update data of form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form or form is in a invalid write state")
	})
	@PutMapping("/{id}")
	public void update(Authentication authentication, @RequestBody Map<String, Object> temp, @PathVariable UUID id) {
		try {
			this.task.updateForm(authentication.getName(), id, temp);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "update data of form and lock to form from future changes")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "A field has an invalid value"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form or form is in a invalid write state")
	})
	@PostMapping("/{id}")
	public void finish(Authentication authentication, @RequestBody Map<String, Object> temp, @PathVariable UUID id) {
		try {
			this.task.finishForm(authentication.getName(), id, temp);
		} catch (InvalidFormElementValue e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Get reviewer's data of form", description = "Returns a flat array with key value pairs.")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form")
	})
	@GetMapping("/{id}/review")
	public Map<String, Object> fetchParamsReviewer(Authentication authentication, @PathVariable UUID id) {
		try {
			return this.task.getFormValuesReviewer(authentication.getName(), id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "update reviewer's part of the form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form or form is in a invalid write state")
	})
	@PutMapping("/{id}/review")
	public void updateReviewer(Authentication authentication, @RequestBody Map<String, Object> temp, @PathVariable UUID id) {
		try {
			this.task.updateFormReviewer(authentication.getName(), id, temp);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "update reviewer's data of form and lock to form from future changes")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "A field has an invalid value"),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "401", description = "User has no access to form or form is in a invalid write state")
	})
	@PostMapping("/{id}/review")
	public void finishReviewer(Authentication authentication, @RequestBody Map<String, Object> temp, @PathVariable UUID id) {
		try {
			this.task.finishFormReviewer(authentication.getName(), id, temp);
		} catch (InvalidFormElementValue e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (InvalidFormAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}
