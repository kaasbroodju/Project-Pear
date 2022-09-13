package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.PersonRepository;
import com.oep.hu.pear.users.data.PersonRepositorys;
import com.oep.hu.pear.users.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PersonService {

	private PersonRepositorys repository;
	private PersonRepository<Person> personRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public Person getPerson(String email) {
		return this.personRepository.getReferenceById(email);
	}

	public Person savePerson(Person person) {
		return this.personRepository.save(person);
	}

	public Set<Person> getUserFavourites(String user) {
		return this.personRepository.getReferenceById(user).getFavourites();
	}

	public void addToUserFavourites(String userEmail, String favouriteEmail) {
		Person person = this.personRepository.getReferenceById(userEmail);
		Person favourite = this.personRepository.getReferenceById(favouriteEmail);
		person.getFavourites().add(favourite);
		this.personRepository.save(person);
	}

	public void removeFromUserFavourites(String userEmail, String favouriteEmail) {
		Person person = this.personRepository.getReferenceById(userEmail);
		Person favourite = this.personRepository.getReferenceById(favouriteEmail);
		person.getFavourites().remove(favourite);
		this.personRepository.save(person);
	}

	@Transactional
	public void setStudentToApprentice(String email) {
		entityManager.createNamedQuery("Person.changeTypeToApprentice").setParameter(1, email).executeUpdate();
	}

	@Transactional
	public void setApprenticeToStudent(String email) {
		entityManager.createNamedQuery("Person.changeTypeToStudent").setParameter(1, email).executeUpdate();
	}

	public Set<Person> getStudentsWithNameOrBadgeName(String name) {
		name = "%" + name + "%";
		return this.repository.findByNameIsLikeOrBadges_NameIsLikeAllIgnoreCase(name);
	}

	public boolean doesPersonExistsByEmail(String email) {
		return this.repository.existsById(email);
	}

	public List<Person> getAll() {
		return this.repository.findByOrderByEmailAsc();
	}
}
