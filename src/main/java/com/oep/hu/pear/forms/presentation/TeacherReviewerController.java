package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.common.Triplet;
import com.oep.hu.pear.forms.data.PendingReviewDAO;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.task.ReviewTask;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("teacher/review")
@AllArgsConstructor
public class TeacherReviewerController {

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

//	@GetMapping("/pending")
//	public List<PendingReviewDAO> getPendingReviews(Authentication authentication) {
//		try {
//			return this.reviewTask.getPendingReviews(authentication.getName()).stream().map(TeacherReviewerController::translateToDTO).toList());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}

	@Operation(deprecated = true)
	@GetMapping("/awaiting/{email}")
	public List<PendingReviewDAO> getMyPendingReviews(Authentication authentication, @PathVariable String email) {
		return this.reviewTask.getAwaitingReviews(email).stream().map(row -> {
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

	@Operation(deprecated = true)
	@GetMapping("/old")
	public List<PendingReviewDAO> getMyOldReviews(Authentication authentication, @RequestParam(value = "page", required = false) Optional<Integer> page, @PathVariable String email) {
		if (page.orElse(0) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page cannot be lower then 0");
		return this.reviewTask.getOldReviews(authentication.getName(), page.orElse(0)).stream().map(TeacherReviewerController::translateToDTO).toList();
	}

	@Operation(deprecated = true)
	@GetMapping("/pending/amount")
	public long getPendingReviewsAmount(Authentication authentication) {
		return this.reviewTask.getPendingReviewsAmount(authentication.getName());
	}

	@Operation(deprecated = true)
	@GetMapping("/search/{name}")
	public List<PersonDTO> getPossibleReviewerBySearchName(@PathVariable String name) {
		return this.reviewTask.searchPersonByNameOrBadge(name).stream().map(p -> new PersonDTO(p.getEmail(), p.getName())).toList();
	}
}
