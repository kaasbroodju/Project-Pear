package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.forms.application.FileUploadService;
import com.oep.hu.pear.forms.application.exceptions.InvalidFormElementTypeException;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.application.formtemplate.element.impl.FileElementService;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.impl.fileelement.FileElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class FormFileTask {

	private RefactorFormService service;
	private FileUploadService fileUploadService;
	private static final String FORM_FILES_LOCATION = "form-files/";
	private FileElementService fileElementService;


	public void saveFileToForm(UUID formId, String elementName, MultipartFile multipartFile) throws IOException, InvalidFormElementTypeException {
		Form form = this.service.getForm(formId);

		if (!(form.getFormElement(elementName) instanceof FileElement fileElement)) throw new InvalidFormElementTypeException();

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileLocation = this.fileUploadService.saveFile(FORM_FILES_LOCATION, formId + "/" + fileName, multipartFile);
		fileElement.getFiles().put(fileName, fileLocation);

		this.service.update(form);
	}

	public Set<String> getFileNamesOfForm(UUID id, String elementName) {
		Form form = this.service.getForm(id);

		FormElement formElement = form.getFormElement(elementName);
		if (formElement instanceof FileElement fileElement) {
			return fileElement.getFiles().keySet();
		} else {
			return new HashSet<>();
		}
	}

	public byte[] getFileFromForm(UUID id, String elementName, String fileName) throws Exception {
		Form form = this.service.getForm(id);

		FormElement formElement = form.getFormElement(elementName);
		if (formElement instanceof FileElement fileElement) {
			String possibleFileLocation = fileElement.getFiles().get(fileName);
			if (possibleFileLocation != null) {
				return this.fileUploadService.getFile(FORM_FILES_LOCATION, possibleFileLocation);
			} else {
				throw new Exception("idk yey");
			}
		} else {
			return new byte[0];
		}
	}

	public byte[] getFilesOfFormInDirZipped(UUID id, String elementName) {
		Form form = this.service.getForm(id);

		FormElement formElement = form.getFormElement(elementName);
		if (formElement instanceof FileElement fileElement) {
			return this.fileUploadService.getFileListZipped(FORM_FILES_LOCATION, fileElement.getFiles());
		} else {
			return new byte[0];
		}
	}

	public void deleteFileFromForm(UUID id, String elementName, String fileName) {
		Form form = this.service.getForm(id);

		if (!(form.getFormElement(elementName) instanceof FileElement fileElement)) return;

		String possibleFileLocation = fileElement.getFiles().remove(fileName);

		if (possibleFileLocation == null) return;

		this.service.update(form);

		// if file is referenced in another form don't delete
		if (this.fileElementService.isFileReferenced(possibleFileLocation)) return;

		try {
			this.fileUploadService.deleteFile(FORM_FILES_LOCATION, possibleFileLocation);
		} catch (IOException e) {
			log.error("Failed to delete file: {" + id + ", " + elementName + ", " + fileName + ", " + possibleFileLocation + "}", e);
		}
	}
}
