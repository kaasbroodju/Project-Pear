package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.assessmentmatrix.presentation.dto.CompetenceNoDescDTO;
import com.oep.hu.pear.forms.application.exceptions.OpenReviewAlreadyExistsException;
import com.oep.hu.pear.forms.presentation.dto.CreateOpenReviewDTO;
import com.oep.hu.pear.forms.presentation.dto.OpenBoardDTO;
import com.oep.hu.pear.forms.task.OpenReviewTask;
import com.oep.hu.pear.forms.task.exception.FormIsLockedException;
import com.oep.hu.pear.forms.task.exception.NoSelfReviewException;
import com.oep.hu.pear.forms.task.exception.ReviewerUnableToGiveDegreeOfAssessmentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/openboard")
@AllArgsConstructor
public class OpenBoardController {

	private OpenReviewTask task;

	@Operation(summary = "Get public board that the user can review")
	@GetMapping
	public List<OpenBoardDTO> getAll(Authentication authentication) {
		return this.task.getAll(authentication.getName()).stream().map(review -> new OpenBoardDTO(review.getId(), review.getTitle(), review.getFormToBeReviewed().getTemplateName(), review.getWantedDegreeOfAssessment(), review.getCompetence() != null ? new CompetenceNoDescDTO(review.getCompetence().getMainCompetence(), review.getCompetence().getSubCompetence(), review.getCompetence().getLevel()): null, review.getPerson().getName())).toList();
	}

	@Operation(summary = "Put form on public board", description = "Put form on public board to be reviewed later")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "304", description = "Form is already reviewed or already on the board."),
			@ApiResponse(responseCode = "404"),
			@ApiResponse(responseCode = "400", description = "Title too short or degree of assessment is null"),
	})
	@PostMapping("/{id}")
	public void addToBoard(Authentication authentication, @PathVariable UUID id, @RequestBody CreateOpenReviewDTO dto) {
		try {
			this.task.addToBoard(authentication.getName(), id, dto.title(), dto.reviewTypeWanted());
		} catch (FormIsLockedException | OpenReviewAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
		} catch (EntityNotFoundException enfe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException ilae) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Claim a form on the public board")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "User is trying to review themselves or user cannot give the review to that's being asked.")
	})
	@PostMapping("/{id}/claim")
	public void claimOpenReview(Authentication authentication, @PathVariable UUID id) {
		try {
			this.task.claimOpenReview(authentication.getName(), id);
		} catch (ReviewerUnableToGiveDegreeOfAssessmentException | NoSelfReviewException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
