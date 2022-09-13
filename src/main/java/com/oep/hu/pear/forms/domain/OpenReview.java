package com.oep.hu.pear.forms.domain;

import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElement;
import com.oep.hu.pear.users.domain.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "open_review_board")
@NoArgsConstructor
@Setter
@Getter
public class OpenReview {
    @Id
    private UUID id;

    private String title = "";
    private DegreeOfAssessment wantedDegreeOfAssessment;
    private Instant createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Form formToBeReviewed;

    @ManyToOne
    private Competence competence;

    @ManyToOne
    private Person person;

    public OpenReview(String title, DegreeOfAssessment wantedDegreeOfAssessment, Form formToBeReviewed, Person person) {
        this.title = title;
        this.wantedDegreeOfAssessment = wantedDegreeOfAssessment;
        this.formToBeReviewed = formToBeReviewed;
        this.createdAt = Instant.now();
        try {
            FormElement element = formToBeReviewed.getElements().stream().filter(formElement -> formElement.getClass() == CompetenceElement.class).findFirst().orElseThrow(NullPointerException::new);
            this.competence = ((CompetenceElement) element).getCompetence();
        } catch (NullPointerException e) {
            this.competence = null;
        }
        this.person = person;
    }
}
