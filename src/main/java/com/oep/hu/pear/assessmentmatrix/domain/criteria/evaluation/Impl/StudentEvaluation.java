package com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Evaluation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class StudentEvaluation extends Evaluation {

    @Column(columnDefinition = "TEXT")
    private String proof = "";

    public StudentEvaluation(Progress progress, String proof) {
        super(progress);
        this.proof = proof;
    }
}
