package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.data.FileElementRepository;
import com.oep.hu.pear.forms.domain.element.impl.fileelement.FileElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@AllArgsConstructor
public class FileElementService extends ElementService<FileElement> {

	private FileElementRepository repository;

	public boolean isFileReferenced(String possibleFileLocation) {
		return this.repository.countByEntries_FileLocation(possibleFileLocation) > 0;
	}

	@Override
	public void setInnerValue(Map<String, Object> dto, FileElement element) {

	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, FileElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, FileElement element) {

	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, FileElement element) {

	}
}
