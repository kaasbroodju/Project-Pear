package com.oep.hu.pear.users.task;

import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.task.exception.NoSelfFavouriteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class FavouriteTask {

	private PersonService service;

	public Set<Person> getFavouritesEmails(String userEmail) {
		return this.service.getUserFavourites(userEmail);
	}

	public void addToFavourites(String userEmail, String favouriteEmail) throws NoSelfFavouriteException {
		if (userEmail.equals(favouriteEmail)) throw new NoSelfFavouriteException("No, you can't favourite yourself");
		this.service.addToUserFavourites(userEmail, favouriteEmail);
	}

	public void removeFromFavourites(String userEmail, String favouriteEmail) {
		this.service.removeFromUserFavourites(userEmail, favouriteEmail);
	}
}
