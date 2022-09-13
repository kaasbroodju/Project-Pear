package com.oep.hu.pear.users.task;

import com.oep.hu.pear.users.application.BadgeService;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.domain.Apprentice;
import com.oep.hu.pear.users.domain.Badge;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminUserTask {

	private PersonService personService;
	private BadgeService badgeService;

	public void updateUser(String userEmail, String name, Boolean isApprentice, List<String> badgeIds) {
		Person person = this.personService.getPerson(userEmail);
		person.setName(name);

		List<Badge> allowedBadges = badgeService.getGroups().get(null);
		List<Badge> matchedBadges = allowedBadges.stream().filter(badge -> badgeIds.contains(badge.getName())).toList();

		allowedBadges.forEach(person.getBadges()::remove);
		matchedBadges.forEach(person.getBadges()::add);

		if (Hibernate.getClass(person) == Student.class && isApprentice != null && isApprentice) this.personService.setStudentToApprentice(userEmail);
		if (Hibernate.getClass(person) == Apprentice.class && isApprentice != null && !isApprentice) this.personService.setApprenticeToStudent(userEmail);

		this.personService.savePerson(person);
	}
}
