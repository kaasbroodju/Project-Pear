package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Student extends InternalPerson {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books = new HashSet<>(Set.of(new Book(this, Period.FIRST_YEAR_PERIOD_A)));

    private Period currentBook = Period.FIRST_YEAR_PERIOD_A;

    @ManyToOne
    private Group group;

    public Student(String email) {
        super(email);
    }

    @Override
    public DegreeOfAssessment[] ableToGiveTypesOfReviews() {
        return new DegreeOfAssessment[]{DegreeOfAssessment.PEER, DegreeOfAssessment.GUILD};
    }

    public Book getCurrentBook() {
        return books.stream().findFirst().orElseThrow(NullPointerException::new);
    }
}
