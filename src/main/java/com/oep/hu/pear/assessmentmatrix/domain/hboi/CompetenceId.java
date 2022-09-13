package com.oep.hu.pear.assessmentmatrix.domain.hboi;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class CompetenceId implements Serializable {

    private String mainCompetence;
    private String subCompetence;
    private short level;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CompetenceId that = (CompetenceId) o;
        return Objects.equals(mainCompetence, that.mainCompetence)
                && Objects.equals(subCompetence, that.subCompetence)
                && Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainCompetence, subCompetence, level);
    }
}
