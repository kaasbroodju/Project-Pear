package com.oep.hu.pear.forms.domain.element.impl.fileelement;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
public class FileEntry {

	private String fileName;
	private String fileLocation;

	public FileEntry(String fileName, String fileLocation) {
		this.fileName = fileName;
		this.fileLocation = fileLocation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FileEntry)) return false;
		FileEntry fileEntry = (FileEntry) o;
		return Objects.equals(fileName, fileEntry.fileName) && Objects.equals(fileLocation, fileEntry.fileLocation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, fileLocation);
	}
}
