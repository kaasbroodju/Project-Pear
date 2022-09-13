package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends InternalPerson {

    @OneToMany(mappedBy = "coach")
    List<Group> groups;

    public Teacher(String email) {
        super(email);
    }

    @Override
    public DegreeOfAssessment[] ableToGiveTypesOfReviews() {
        return new DegreeOfAssessment[]{DegreeOfAssessment.GUILD, DegreeOfAssessment.GUILD_MASTER};
    }
}
