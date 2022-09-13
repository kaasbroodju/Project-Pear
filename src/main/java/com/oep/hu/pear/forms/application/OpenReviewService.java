package com.oep.hu.pear.forms.application;

import com.oep.hu.pear.forms.application.exceptions.OpenReviewAlreadyExistsException;
import com.oep.hu.pear.forms.data.OpenReviewRepository;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.OpenReview;
import com.oep.hu.pear.users.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OpenReviewService {

    private OpenReviewRepository repository;

    public void addOpenReviewToBoard(String title, DegreeOfAssessment degreeOfAssessmentNeeded, Form form, Person person) throws OpenReviewAlreadyExistsException {
        if (this.repository.existsByFormToBeReviewed(form)) throw new OpenReviewAlreadyExistsException();
        this.repository.save(new OpenReview(title, degreeOfAssessmentNeeded, form, person));
    }

    public void removeOpenReviewFromBoard(UUID id) {
        this.repository.deleteById(id);
    }

    public List<OpenReview> getAll(Person person) {
        return this.repository.findByPersonIsNotAndWantedDegreeOfAssessmentIsIn(person, List.of(person.ableToGiveTypesOfReviews()));
    }

    public void deleteAllOneMonthAgo() {
        this.repository.deleteByCreatedAtIsBefore(Instant.now().minus(1, ChronoUnit.MONTHS));
    }

    public OpenReview getOpenReview(UUID id) {
        return this.repository.getReferenceById(id);
    }
}
