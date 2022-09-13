package com.oep.hu.pear.forms.domain;

public enum ReviewPolicy {
	DEFAULT, // Student is able to pick any reviewer and pin to board.
	DEFAULT_NO_BOARD, // Student is able to pick any reviewer but not able pin to board.
	AUTOMATIC_TO_GROUP, // Student does not need to pick a peer reviewer it's automatically send to all peers in a group.
	AUTOMATIC_COACH, // Review is send to Student's coach automatically.
	MANUALLY_TEACHER, // Review can only be conduct by a teacher.
}
