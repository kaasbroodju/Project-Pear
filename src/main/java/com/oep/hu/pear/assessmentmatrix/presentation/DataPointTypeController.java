package com.oep.hu.pear.assessmentmatrix.presentation;

import com.oep.hu.pear.assessmentmatrix.application.DataPointTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/datapointtype")
@AllArgsConstructor
public class DataPointTypeController {

	private DataPointTypeService service;

	@Operation(summary = "Get list of all datapointtypes")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping
	public List<String> getAllTypes() {
		return this.service.getAllTypes();
	}
}
