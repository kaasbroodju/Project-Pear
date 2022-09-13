package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.forms.application.FileUploadService;
import com.oep.hu.pear.forms.application.exceptions.InvalidFormElementTypeException;
import com.oep.hu.pear.forms.task.FormFileTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/form")
@AllArgsConstructor
public class FormFileController {

	private FileUploadService fileUploadService;
	private FormFileTask task;
	private static final String FORM_FILES_LOCATION = "form-files/";
	private static final String FORM_MARKDOWN_FILES_LOCATION = "form-markdown-files/";

	@Operation(summary = "Upload file to element in form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Form element is not an element a file can be uploaded to"),
			@ApiResponse(responseCode = "404", description = "Form or element not found")
	})
	@PostMapping("/{id}/file/{elementName}")
	public void uploadFile(@RequestParam("image") MultipartFile multipartFile, @PathVariable UUID id, @PathVariable String elementName) {
		try {
			this.task.saveFileToForm(id, elementName, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidFormElementTypeException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Get list of file descriptors of form element")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Form or element not found")
	})
	@GetMapping("/{id}/file/{elementName}/names")
	public Set<String> getFiles(@PathVariable UUID id, @PathVariable String elementName) {
		return this.task.getFileNamesOfForm(id, elementName);
	}

	@Operation(summary = "Get file from element in form")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "404", description = "Form, file or element not found")
	})
	@GetMapping(value = "/{id}/file/{elementName}/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] getFile(@PathVariable UUID id, @PathVariable String elementName, @PathVariable String name) {
		try {
			return this.task.getFileFromForm(id, elementName, name);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete file from element in form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Form, file or element not found")
	})
	@DeleteMapping(value = "/{id}/file/{elementName}/{name}")
	public void deleteFile(@PathVariable UUID id, @PathVariable String elementName, @PathVariable String name) {
		try {
			this.task.deleteFileFromForm(id, elementName, name);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Upload file to element in form")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Form not found")
	})
	@PostMapping("/{id}/markdown/file")
	public String uploadFileInMarkdown(@RequestParam("image") MultipartFile multipartFile, @PathVariable String id) {
		String uuid = UUID.randomUUID().toString();
		try {
			return this.fileUploadService.saveFile(FORM_MARKDOWN_FILES_LOCATION + "/" + id, id + "/" + uuid, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EntityNotFoundException enfe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get file that's in the markdown text")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "404", description = "Form, file or element not found")
	})
	@GetMapping(value = "/{id}/markdown/file/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] getFileInMarkdown(@PathVariable String id, @PathVariable String name) {
		try {
			return this.fileUploadService.getFile(FORM_MARKDOWN_FILES_LOCATION, id + "/" + name);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (EntityNotFoundException enfe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get all files zipped of from element in form")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "404", description = "Form, file or element not found")
	})
	@GetMapping(value = "/{id}/file/{elementName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] getFilesZipped(@PathVariable UUID id, @PathVariable String elementName) {
		try {
			return this.task.getFilesOfFormInDirZipped(id, elementName);
		} catch (EntityNotFoundException enfe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
