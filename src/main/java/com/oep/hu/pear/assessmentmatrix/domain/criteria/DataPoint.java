package com.oep.hu.pear.assessmentmatrix.domain.criteria;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.ReviewerEvaluation;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.evaluation.Impl.StudentEvaluation;
import com.oep.hu.pear.forms.domain.Form;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(DataPointId.class)
@Setter
@Getter
@NoArgsConstructor
public class DataPoint {

    @Id
    @ManyToOne
    private DataPointType type;

    @Id
    @ManyToOne
//    @PrimaryKeyJoinColumn(columnDefinition = "criteria_id", referencedColumnName = "from_id")
    private Form form;

    @Embedded @Setter
    @AttributeOverrides({
            @AttributeOverride(name = "progress", column = @Column(name = "progress_student"))
    })
    private StudentEvaluation studentEvaluation = new StudentEvaluation();

    @Embedded @Setter
    @AttributeOverrides({
            @AttributeOverride(name = "progress", column = @Column(name = "progress_reviewer"))
    })
    private ReviewerEvaluation reviewerEvaluation = new ReviewerEvaluation();

    public DataPoint(DataPointType type, Form reviewableForm) {
        this.type = type;
        this.form = reviewableForm;
    }
}
