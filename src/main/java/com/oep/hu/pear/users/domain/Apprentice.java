package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Apprentice extends Student {

    public Apprentice(String email) {
        super(email);
    }

//    public Apprentice(Student student) {
//        super(student.getEmail(), student.getDrafts(), student.getForms(), student.getFavourites());
////        this.setMyGuild(student.getMyGuild());
//        student.getMyGuild().addPerson(this);
//        student.getMyGuild().removePerson(student);
//    }

    @Override
    public DegreeOfAssessment[] ableToGiveTypesOfReviews() {
        return new DegreeOfAssessment[]{DegreeOfAssessment.APPRENTICE, DegreeOfAssessment.PEER, DegreeOfAssessment.GUILD};
    }
}
