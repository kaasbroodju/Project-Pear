package com.oep.hu.pear.forms.data;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class PendingReviewDAO {
	private UUID id;
	private String name;
	private String templateName;
	private String studentName;
	private String mainCompetence;
	private String subCompetence;
	private Short level;
	private Instant finishedAt;


	public PendingReviewDAO(UUID id, String name, String templateName, String studentName, String mainCompetence, String subCompetence, Short level, Instant finishedAt) {
		this.id = id;
		this.name = name;
		this.templateName = templateName;
		this.studentName = studentName;
		this.mainCompetence = mainCompetence;
		this.subCompetence = subCompetence;
		this.level = level;
		this.finishedAt = finishedAt;
	}
}
