package com.oep.hu.pear.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Guild {
    @Id
    private String name;

    @OneToMany(mappedBy = "myGuild")
    private Set<InternalPerson> persons = new HashSet<>();

    public Guild(String name) {
        this.name = name;
    }

    public void addPerson(InternalPerson person) {
        persons.add(person);
        person.setMyGuild(this);
    }

    public void removePerson(InternalPerson person) {
        persons.remove(person);
        person.setMyGuild(null);
    }

    public Set<Teacher> getGuildMasters() {
        return this.persons.stream().filter(person -> person.getClass().equals(Teacher.class)).map(person -> (Teacher) person).collect(Collectors.toSet());
    }

    public Set<Apprentice> getApprentices() {
        return this.persons.stream().filter(person -> person.getClass().equals(Apprentice.class)).map(person -> (Apprentice) person).collect(Collectors.toSet());
    }

    public Set<Student> getStudents() {
        return this.persons.stream().filter(person -> person.getClass().equals(Student.class)).map(person -> (Student) person).collect(Collectors.toSet());
    }
}
