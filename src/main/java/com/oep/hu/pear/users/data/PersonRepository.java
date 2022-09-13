package com.oep.hu.pear.users.data;

import com.oep.hu.pear.users.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository<T extends Person> extends JpaRepository<T, String> {


	List<Person> findByFavourites_Badges_NameLikeOrNameLike(String name, String name1);
}
