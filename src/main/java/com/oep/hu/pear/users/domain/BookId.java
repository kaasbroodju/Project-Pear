package com.oep.hu.pear.users.domain;

import java.io.Serializable;
import java.util.Objects;

public class BookId implements Serializable {

	private Student student;
	private Period period;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookId bookId = (BookId) o;
		return Objects.equals(student, bookId.student) && Objects.equals(period, bookId.period);
	}

	@Override
	public int hashCode() {
		return Objects.hash(student, period);
	}
}
