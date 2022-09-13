package com.oep.hu.pear.assessmentmatrix.domain.hboi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompetenceId.class)
@Getter
public class Competence {
    @Id
    private String mainCompetence;
    @Id
    private String subCompetence;
    @Id
    private short level;

    @Column(columnDefinition = "TEXT")
    private String description;
}
