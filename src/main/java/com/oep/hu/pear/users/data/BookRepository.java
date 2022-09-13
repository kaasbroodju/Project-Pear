package com.oep.hu.pear.users.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.users.domain.Book;
import com.oep.hu.pear.users.domain.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, BookId> {
	Book findByForms(Form forms);


}
