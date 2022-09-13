package com.oep.hu.pear.users.task;

import com.oep.hu.pear.users.application.BadgeService;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.domain.Badge;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.task.exception.RestrictedBadgeException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class BadgeTask {

	private PersonService personService;
	private BadgeService badgeService;

	public Set<Badge> getPersonsBadges(String email) {
		return this.personService.getPerson(email).getBadges();
	}

	public void addBadgeToPerson(String email, String badgeName) {
		this.personService.getPerson(email).getBadges().add(this.badgeService.getBadge(badgeName));
	}

	public Badge createBadge(String badgeName, String groupName) {
		return this.badgeService.createBadge(badgeName, groupName);
	}

	public Map<String, List<Badge>> getBadgesGroupedByGroup() {
		return this.badgeService.getGroups();
	}

	public void removeBadgeFromUser(String email, String badgeName, boolean restrictedAllowed) throws RestrictedBadgeException {
		Person person = this.personService.getPerson(email);
		Badge badge = this.badgeService.getBadge(badgeName);
		if (badge.getBadgeGroup() == null && !restrictedAllowed) throw new RestrictedBadgeException();
		person.getBadges().remove(badge);
		this.personService.savePerson(person);
	}

	public void addBadgeToUser(String email, String badgeName, boolean restrictedAllowed) throws RestrictedBadgeException {
		Person person = this.personService.getPerson(email);
		Badge badge = this.badgeService.getBadge(badgeName);
		if (badge.getBadgeGroup() == null && !restrictedAllowed) throw new RestrictedBadgeException();
		person.getBadges().add(badge);
		this.personService.savePerson(person);
	}
}
