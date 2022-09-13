package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@NamedNativeQuery(name="Person.changeTypeToApprentice", query="UPDATE person SET dtype = 'Apprentice' where email = ? and dtype = 'Student'")

@NamedNativeQuery(name="Person.changeTypeToStudent", query="UPDATE person SET dtype = 'Student' where email = ? and dtype = 'Apprentice'")

public abstract class Person {
    @Id
    protected String email;

    protected String name;

    @OneToMany
    protected Set<Person> favourites = new HashSet<>();

    @ManyToMany
    protected Set<Badge> badges = new HashSet<>();

    public Person(String email) {
        this.email = email;
        this.name = Arrays.stream(email.split("@")[0].replaceAll("\\.", " ").split("\\s")).map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1)).collect(Collectors.joining(" "));
    }

    public abstract DegreeOfAssessment[] ableToGiveTypesOfReviews();
}
