package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.common.Triplet;
import com.oep.hu.pear.forms.data.PendingReviewDAO;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.presentation.dto.ReviewDTO;
import com.oep.hu.pear.forms.task.ReviewTask;
import com.oep.hu.pear.forms.task.exception.FormIsLockedException;
import com.oep.hu.pear.forms.task.exception.NoSelfReviewException;
import com.oep.hu.pear.forms.task.exception.ReviewerUnableToGiveDegreeOfAssessmentException;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewerController {

	private ReviewTask reviewTask;

	private static PendingReviewDAO translateToDTO(Triplet<Form, Person, Competence> row) {
		Form form = row.getFirst();
		Person person = row.getSecond();
		Competence competence = row.getThird();

		if (competence == null) {
			return new PendingReviewDAO(form.getId(), form.getName(), form.getTemplateName(), person.getName(), null, null, null, form.getFinishedAt());
		} else {
			return new PendingReviewDAO(form.getId(), form.getName(), form.getTemplateName(), person.getName(), competence.getMainCompetence(), competence.getSubCompetence(), competence.getLevel(), form.getFinishedAt());
		}
	}

	@Operation(summary = "Create review on form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "User cannot review themselves or review cannot give degree of assessment"),
			@ApiResponse(responseCode = "304", description = "Form is already under review or done"),
			@ApiResponse(responseCode = "404", description = "Form does not exist"),
	})
	@PostMapping("/{id}")
	public void createReview(Authentication authentication, @PathVariable UUID id, @RequestBody ReviewDTO reviewDTO) {
		try {
			this.reviewTask.addReviewer(authentication.getName(), id, reviewDTO.reviewerEmail(), reviewDTO.degreeOfAssessment());
		} catch (NoSelfReviewException | ReviewerUnableToGiveDegreeOfAssessmentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (FormIsLockedException e) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Decline a review that was sent to user")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "304", description = "Form is not under review"),
			@ApiResponse(responseCode = "404", description = "Form does not exist"),
	})
	@DeleteMapping("/{id}")
	public void rejectReview(Authentication authentication, @PathVariable UUID id) {
		try {
			this.reviewTask.rejectReview(authentication.getName(), id);
		} catch (FormIsLockedException e) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get list of the specified user's degree of assessment user can do")
	@GetMapping("/type/{email}")
	public DegreeOfAssessment[] getReviewTypesOfPerson(@PathVariable String email) {
		return this.reviewTask.getReviewTypesOfPerson(email);
	}

	@Operation(summary = "Get list of user's pending reviews", description = "Pending, meaning other users are waiting for user to complete the review")
	@GetMapping("/pending")
	public List<PendingReviewDAO> getPendingReviews(Authentication authentication) {
		return this.reviewTask.getPendingReviews(authentication.getName()).stream().map(ReviewerController::translateToDTO).toList();
	}

	@Operation(summary = "Get list of user's awaiting reviews", description = "Awaiting, meaning user is waiting for other users to complete their review")
	@GetMapping("/awaiting")
	public List<PendingReviewDAO> getMyPendingReviews(Authentication authentication) {
		return this.reviewTask.getAwaitingReviews(authentication.getName()).stream().map(row -> {
			Form form = row.getFirst();

			Person person = row.getFirst().getReviewer();
			Competence competence = row.getThird();

			if (competence == null) {
				return new PendingReviewDAO(form.getId(), form.getName(), form.getTemplateName(), person.getName(), null, null, null, form.getFinishedAt());
			} else {
				return new PendingReviewDAO(form.getId(), form.getName(), form.getTemplateName(), person.getName(), competence.getMainCompetence(), competence.getSubCompetence(), competence.getLevel(), form.getFinishedAt());
			}
		}).toList();
	}

	@Operation(summary = "Get old reviews done by user",
			parameters = @Parameter(name = "from", description = "Optional time since EPOCH, to reduce unnecessary throughput.")
	)
	@ApiResponse(responseCode = "400", description = "Invalid time instant format")
	@GetMapping(value = "/old")
	public List<PendingReviewDAO> getMyOldReviewsAfter(Authentication authentication, @RequestParam(value = "from", required = false) Optional<String> instantString) {
		Instant afterInstant = Instant.EPOCH;;
		try {
			if (instantString.isPresent()) {
				afterInstant = Instant.parse(instantString.get());
			}
		} catch (DateTimeParseException dtpe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return this.reviewTask.getOldReviewsAfter(authentication.getName(), afterInstant).stream().map(ReviewerController::translateToDTO).toList();
	}

	@Operation(summary = "Get number of pending reviews user has")
	@GetMapping("/pending/amount")
	public long getPendingReviewsAmount(Authentication authentication) {
		return this.reviewTask.getPendingReviewsAmount(authentication.getName());
	}

	@Operation(summary = "Search possible reviewer by name or badge name")
	@GetMapping("/search/{name}")
	public List<PersonDTO> getPossibleReviewerBySearchName(@PathVariable String name) {
		return this.reviewTask.searchPersonByNameOrBadge(name).stream().map(p -> new PersonDTO(p.getEmail(), p.getName())).toList();
	}
}
