package com.oep.hu.pear.users.domain;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@IdClass(BookId.class)
@Getter
public class Book implements Serializable {

	@Id
	@OneToOne
	private Student student;

	@Id
	@Enumerated(value = EnumType.STRING)
	private Period period;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Form> forms = new HashSet<>();


	public Book(Student student, Period period) {
		this.student = student;
		this.period = period;
	}

	public Set<Form> getDrafts() {
		return forms.stream().filter(form -> form.getFormStatus() == FormStatus.CONCEPT || form.getFormStatus() == FormStatus.IN_LIMBO).collect(Collectors.toSet());
	}

	public Set<Form> getDones() {
		return forms.stream().filter(form -> form.getFormStatus() == FormStatus.DONE).collect(Collectors.toSet());
	}

	public Set<Form> getUnderReview() {
		return forms.stream().filter(form -> form.getFormStatus() == FormStatus.UNDER_REVIEW).collect(Collectors.toSet());
	}
}
