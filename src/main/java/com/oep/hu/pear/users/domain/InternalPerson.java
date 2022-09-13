package com.oep.hu.pear.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter @Setter
public abstract class InternalPerson extends Person {

	@ManyToOne
	private Guild myGuild;

	public InternalPerson(String email) {
		super(email);
	}
}
