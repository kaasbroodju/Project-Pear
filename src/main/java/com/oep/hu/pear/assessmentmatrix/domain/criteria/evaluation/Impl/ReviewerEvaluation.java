package com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Evaluation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class ReviewerEvaluation extends Evaluation {

    @Column(columnDefinition = "TEXT")
    private String compliment = "";

    @Column(columnDefinition = "TEXT")
    private String feedback = "";

    public ReviewerEvaluation(Progress progress, String compliment, String feedback) {
        super(progress);
        this.compliment = compliment;
        this.feedback = feedback;
    }
}
