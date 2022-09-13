package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import com.oep.hu.pear.users.presentation.dto.EmotionDTO;
import com.oep.hu.pear.users.presentation.dto.ProgressCriteriaDTO;
import com.oep.hu.pear.users.presentation.dto.ProgressDurationDTO;
import com.oep.hu.pear.users.task.ProgressTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/progress")
@AllArgsConstructor
public class ProgressController {

	private ProgressTask task;

	private ProgressCriteriaDTO translateToDTO(DataPointElement dataPoint) {
		return new ProgressCriteriaDTO(
				dataPoint.getForm().getId(),
				dataPoint.getForm().getTemplateName(),
				dataPoint.getReviewerEvaluation().getProgress(),
				dataPoint.getForm().getDegreeOfAssessment(),
				dataPoint.getForm().getFinishedAt()
			);
	}

//	@GetMapping()
//	public Map<String, List<ProgressCriteriaDTO>> getStudentProgress() {
//		Map<String, List<ProgressCriteriaDTO>> dto = new HashMap<>();
//		List<DataPointElement> dataPointList = this.task.dataPointListOfCurrentBookOfStudent(UserSingleton.getInstance().getUser().getUsername());
//		for (String types : this.dataPointTypeService.getAllTypes()) {
//			dto.put(types, new ArrayList<>());
//		}
//		for (DataPointElement dataPoint : dataPointList) {
//			dto.get(dataPoint.getName()).add(this.translateToDTO(dataPoint));
//		}
//		return dto;
//	}

	@Operation(summary = "Get user's (student) progress on a specific datapoint")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
	})
	@GetMapping("/{dataPointType}")
	public List<ProgressCriteriaDTO> getStudentProgressOnDataPointType(Authentication authentication, @PathVariable String dataPointType) {
		return this.task.dataPointListOfCurrentBookOfStudent(authentication.getName(), dataPointType).stream()
				.map(this::translateToDTO).toList();
	}

	@Operation(summary = "Get user's time spent in their current book")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping("/time")
	public List<ProgressDurationDTO> getStudentProgressOnTime(Authentication authentication) {
		return this.task.durationListOfCurrentBookOfStudent(authentication.getName()).stream()
				.map(durationElement -> new ProgressDurationDTO(durationElement.getForm().getFinishedAt(), durationElement.getDuration().toMillis())).toList();
	}

	@Operation(summary = "Get user's (student) emotions over time")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping("/emotion")
	public List<EmotionDTO> getStudentEmotionOverTime(Authentication authentication) {
		return this.task.emotionListOfCurrentBookOfStudent(authentication.getName()).stream()
				.map(durationElement -> new EmotionDTO(durationElement.getForm().getFinishedAt(), durationElement.getEmotion())).toList();
	}
}
