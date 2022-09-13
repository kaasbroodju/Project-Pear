package com.oep.hu.pear.security.domain;

import lombok.Getter;

import java.text.MessageFormat;


public enum RegisterRole {
	STUDENT(15L, "Complete your registration at: {0}"),
	APPRENTICE(15L, "Complete your registration at: {0}"),
	TEACHER(4320L, "Complete your registration at: {0}"),
	EXTERN(4320L, "Complete your registration at: {0}");

	private static final String FRONTEND_REGISTRATION_LOCATION = "www.project-pear.com/register/";


	@Getter
	private final long availableTimeInvite;
	private final String message;

	RegisterRole(long availableTimeInvite, String message) {
		this.availableTimeInvite = availableTimeInvite;
		this.message = message;
	}

	public String getFormattedMessage(String inviteLink) {
		return MessageFormat.format(this.message, FRONTEND_REGISTRATION_LOCATION + inviteLink);
	}
}
