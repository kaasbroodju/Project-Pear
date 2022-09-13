package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.OpenReview;
import com.oep.hu.pear.users.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface OpenReviewRepository extends JpaRepository<OpenReview, UUID> {
	long deleteByCreatedAtIsBefore(Instant createdAt);

	@Query("select (count(o) > 0) from OpenReview o where o.formToBeReviewed = ?1")
	boolean existsByFormToBeReviewed(Form formToBeReviewed);



	List<OpenReview> findByPersonIsNotAndWantedDegreeOfAssessmentIsIn(Person person, Collection<DegreeOfAssessment> wantedDegreeOfAssessments);



//	boolean existsByFormToBeReviewed(Form formToBeReviewed);
}
