package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.template.FormTemplate;
import com.oep.hu.pear.users.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormTemplateRepository extends JpaRepository<FormTemplate, String> {
	List<FormTemplate> findByCanBeCreatedBy(Class<? extends Person> canBeCreatedBy);

}
