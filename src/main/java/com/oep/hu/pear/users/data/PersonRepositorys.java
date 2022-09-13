package com.oep.hu.pear.users.data;

import com.oep.hu.pear.users.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PersonRepositorys extends JpaRepository<Person, String> {
	List<Person> findByOrderByEmailAsc();

//	List<Person> findByFavourites_Badges_NameLikeOrNameLike(String name, String name1);
//
//	@Query("select p from Person p left join p.badges badges where badges.name like ?1 or p.name like ?2")
//	List<Person> findByBadges_NameIsLikeOrNameIsLike(String name, String name1);
//
//	@Query("""
//			select p from Person p left join p.badges badges
//			where upper(p.name) like upper(?1) or upper(badges.name) like upper(?2)""")
//	List<Person> findByNameIsLikeIgnoreCaseOrBadges_NameIsLikeIgnoreCase(String name, String name1);
//
//	@Query("""
//			select p from Person p left join p.badges badges
//			where upper(badges.name) like upper(?1) or upper(p.name) like upper(?1) or upper(p.email) like upper(?1)""")
//	Set<Person> findDistinctByBadges_NameIsLikeOrNameIsLikeOrEmailIsLikeAllIgnoreCase(String name);

	@Query("""
			select p from Person p left join p.badges badges
			where upper(p.name) like upper(?1) or (upper(badges.name) like upper(?1) and length(badges.name) <= length(?1))""")
	Set<Person> findByNameIsLikeOrBadges_NameIsLikeAllIgnoreCase(String name);


	

//	List<Person> findByNameIsLikeIgnoreCaseOrBadges_NameIsLikeIgnoreCase(String name, String name1);




	
	


}
