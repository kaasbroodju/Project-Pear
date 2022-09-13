package com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Evaluation {
    protected Progress progress;
}
