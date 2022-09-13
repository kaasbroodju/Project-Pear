package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.forms.application.FileUploadService;
import com.oep.hu.pear.users.task.GuildTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class UserSettingsController {

	private GuildTask guildTask;
	private FileUploadService fileUploadService;
	private static final String PROFILE_PICTURES_DIRECTORY = "profile-pictures/";

	@Operation(summary = "Change users 'main' guild")
	@PatchMapping("/guild/{guild}")
	public void changeGuild(Authentication authentication, @PathVariable String guild) {
		this.guildTask.changeGuild(authentication.getName(), guild);
	}

	@Operation(summary = "Upload endpoint for profile picture of user", requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))
	@PostMapping("/user/picture")
	public void uploadFile(Authentication authentication, @RequestParam("image") MultipartFile multipartFile) {
		try {
			this.fileUploadService.saveFile(PROFILE_PICTURES_DIRECTORY, authentication.getName(), multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Gets users profile picture")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User has a profile picture", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "204", description = "User has no profile picture")
	})
	@GetMapping("/user/picture")
	public byte[] getProfilePicture(Authentication authentication) {
		try {
			return this.fileUploadService.getFileHashed(PROFILE_PICTURES_DIRECTORY, authentication.getName(), ".png");
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
	}
}
