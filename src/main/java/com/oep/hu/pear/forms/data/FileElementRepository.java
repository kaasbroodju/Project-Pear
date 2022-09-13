package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.element.FormElementId;
import com.oep.hu.pear.forms.domain.element.impl.fileelement.FileElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileElementRepository extends JpaRepository<FileElement, FormElementId> {


	@Query("select count(f) from FileElement f inner join f.files files where value(files) = ?1")
	long countByEntries_FileLocation(String fileLocation);








}
