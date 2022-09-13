package com.oep.hu.pear;

import com.oep.hu.pear.assessmentmatrix.application.CompetenceService;
import com.oep.hu.pear.assessmentmatrix.application.DataPointTypeService;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.forms.application.exceptions.OpenReviewAlreadyExistsException;
import com.oep.hu.pear.forms.application.formtemplate.FormTemplateService;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.ReviewPolicy;
import com.oep.hu.pear.forms.domain.element.impl.assessmentelement.AssessmentElement;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElement;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElementValidator;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.*;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentActionElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentGoalOnDataPointTypeElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.ReviewableDataPointTypeSelectorElement;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElement;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElementValidator;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.Emotion;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElement;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElementValidator;
import com.oep.hu.pear.forms.domain.element.impl.fileelement.FileElement;
import com.oep.hu.pear.forms.domain.element.impl.sectionelement.SectionElement;
import com.oep.hu.pear.forms.domain.element.impl.textelement.ReviewableTextElement;
import com.oep.hu.pear.forms.domain.element.impl.textelement.TextElement;
import com.oep.hu.pear.forms.domain.element.impl.textelement.TextElementValidator;
import com.oep.hu.pear.forms.domain.template.FormTemplate;
import com.oep.hu.pear.forms.domain.template.FormTemplateBuilder;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayoutBuilder;
import com.oep.hu.pear.forms.domain.template.TemplateElement;
import com.oep.hu.pear.forms.task.FormTask;
import com.oep.hu.pear.forms.task.OpenReviewTask;
import com.oep.hu.pear.forms.task.ReviewTask;
import com.oep.hu.pear.forms.task.exception.*;
import com.oep.hu.pear.security.application.RoleService;
import com.oep.hu.pear.security.application.UserDetailsServiceImpl;
import com.oep.hu.pear.security.task.RegisterTask;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.application.TeacherService;
import com.oep.hu.pear.users.domain.Apprentice;
import com.oep.hu.pear.users.domain.Student;
import com.oep.hu.pear.users.task.BadgeTask;
import com.oep.hu.pear.users.task.GroupTask;
import com.oep.hu.pear.users.task.GuildTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class StartUp implements CommandLineRunner {

	private CompetenceService service;
	private DataPointTypeService dataPointTypeService;
	private FormTemplateService formTemplateService;
	private FormTask formTask;
	private StudentService studentService;
	private TeacherService teacherService;
	private GuildTask guildTask;
	private ReviewTask reviewTask;
	private GroupTask groupTask;
	private BadgeTask badgeTask;
	private OpenReviewTask openReviewTask;
	private UserDetailsServiceImpl userDetailsService;
	private RoleService roleService;
	private RegisterTask registerTask;

	private static final String[] types = new String[]{"Juiste kennis ontwikkelen", "Kwalatief product maken", "Creatief werken", "Kritisch oordelen", "Samenwerken", "Boodschap overbrengen", "Plannen", "Aanpassingsvermogen", "Proactief handelen", "Reflecteren"};
	private static final String[] guilds = new String[]{"back-end gilde", "front-end gilde", "csc gilde", "po gilde", "ti gilde", "ai gilde"};
	private static final String[] mainCompetences = new String[]{"gebruikers interactie", "organisatie processen", "infrastructuur", "software", "hardware interfacing"};
	private static final String[] subCompetences = new String[]{"analyseren", "adviseren", "ontwerpen", "realiseren", "manage en control"};


	@Override @Transactional
	public void run(String... args) throws Exception {
		if (System.getenv("secret_key") == null) {
			throw new RuntimeException("Environment variable 'secret_key' is not set");
		}

		if (System.getenv("file_salt") == null) {
			throw new RuntimeException("Environment variable 'file_salt' is not set");
		}

		this.roleService.createRole("ROLE_ADMIN");
		this.roleService.createRole("ROLE_TEACHER");

		{
			String adminEmail = System.getenv("admin_email");
			String adminPassword = System.getenv("admin_password");

			if (adminEmail != null && adminPassword != null) {
				this.userDetailsService.createAdmin(adminEmail, adminPassword);
			} else if (!this.userDetailsService.isAdminSet()) {
				log.error("Admin is not set.");
			}
		}

		createCompetences();

		createDataPoints();

		createBadges();


		List<DataPointType> dataPointTypes = this.dataPointTypeService.getAllTypes().stream().map(dataPointType -> dataPointTypeService.getDataPointType(dataPointType)).toList();

		// create dev users
		String teacherEmail = "dev.teacher@hu.nl";
		String apprenticeEmail = "dev.apprentice@student.hu.nl";
		String studentEmail = "dev.student@student.hu.nl";

		this.userDetailsService.createTeacher(teacherEmail, "password");
		this.userDetailsService.createUser(apprenticeEmail, "password");
		this.userDetailsService.createUser(studentEmail, "password");

		teacherService.createTeacher(teacherEmail);
		studentService.createStudent(apprenticeEmail);
		studentService.createStudent(studentEmail);

		// create guilds
		for (String guild : guilds) {
			guildTask.createGuild(guild);
		}

		guildTask.addMaster(guilds[0], teacherEmail);
		guildTask.addStudent(guilds[0], studentEmail);
		guildTask.addStudent(guilds[0], apprenticeEmail);
		guildTask.promoteStudentToApprentice(guilds[0], apprenticeEmail);

		// create group 'Project Pear'
		groupTask.createGroup("Project Pear");
		groupTask.addStudentToGroup("Project Pear", studentEmail);
		groupTask.addStudentToGroup("Project Pear", apprenticeEmail);
		groupTask.changeCoach("Project Pear", teacherEmail);

		createFormTemplates(dataPointTypes);

		fillFormDataToStudentAndApprentice(dataPointTypes, apprenticeEmail, studentEmail);

		log.info("start up script ended");
	}

	private void createFormTemplates(List<DataPointType> dataPointTypes) {
		{
			FormTemplate dailystandup = this.formTemplateService.create(
				new FormTemplateBuilder("daily standup")
					.addCreator(Student.class)
					.addCreator(Apprentice.class)
					.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate("daily standup",
				new FormTemplateLayoutBuilder("daily standup")
					.addElement(new TemplateElement("checkin", SectionElement.class, "check-in", null))
					.addElement(new TemplateElement("emotion", EmotionElement.class, "hoe voel je vandaag?", new EmotionElementValidator()))
					.addElement(new TemplateElement("why", TextElement.class, "waarom voel je, je zo?", null))
					.addElement(new TemplateElement("plannedActivities", TextElement.class, "wat ga je vandaag doen?", new TextElementValidator()))
					.addElement(new TemplateElement("questions", TextElement.class, "heb je eventueel vragen?", null))
					.addElement(new TemplateElement("checkout", SectionElement.class, "check-out", null))
					.addElement(new TemplateElement("executedActivities", TextElement.class, "wat heb je vandaag gedaan?", new TextElementValidator()))
					.addElement(new TemplateElement("duration", DurationElement.class, "hoeveel heb je vandaag gewerkt?", new DurationElementValidator()))
					.addElement(new TemplateElement("learned", TextElement.class, "wat heb je vandaag geleerd", new TextElementValidator()))
					.build()
			);
		}

		{
			FormTemplate productreview = this.formTemplateService.create(
					new FormTemplateBuilder("product review")
							.setReviewPolicy(ReviewPolicy.DEFAULT)
							.setMaxAllowedDrafts(15)
							.addCreator(Student.class)
							.addCreator(Apprentice.class)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"product review",
					new FormTemplateLayoutBuilder("product review")
							.addElement(new TemplateElement("productInformation", SectionElement.class, "product informatie", null))

							.addElement(new TemplateElement("description", TextElement.class, "omschrijving", null))
							.addElement(new TemplateElement("acceptCriteria", TextElement.class, "acceptatiecriteria", null))
							.addElement(new TemplateElement("qualityCriteria", TextElement.class, "kwaliteitscriteria", null))
							.addElement(new TemplateElement("files", FileElement.class, "bestanden", null))

							.addElement(new TemplateElement("skills", SectionElement.class, "vaardigheden", null))
							.addElement(new TemplateElement(dataPointTypes.get(0).getType(), NotApplicableDataPointElement.class, dataPointTypes.get(0).getType(), null))
							.addElement(new TemplateElement(dataPointTypes.get(1).getType(), DataPointElement.class, dataPointTypes.get(1).getType(), new DataPointElementValidator()))
							.addElement(new TemplateElement(dataPointTypes.get(2).getType(), NotApplicableDataPointElement.class, dataPointTypes.get(2).getType(), null))
							.addElement(new TemplateElement(dataPointTypes.get(3).getType(), NotApplicableDataPointElement.class, dataPointTypes.get(3).getType(), null))
							.addElement(new TemplateElement("HBOICompetence", SectionElement.class, "HBO-I compentencie", null))

							.addElement(new TemplateElement("competence", CompetenceElement.class, "competentie", new CompetenceElementValidator()))
							.build()

			);
		}

		{
			List<DataPointType> bannedList = new ArrayList<>();
			bannedList.add(dataPointTypes.get(0));
			bannedList.add(dataPointTypes.get(5));
			FormTemplate kennisdeling = this.formTemplateService.create(
					new FormTemplateBuilder("kennisdeling")
							.setReviewPolicy(ReviewPolicy.DEFAULT)
							.setMaxAllowedDrafts(4)
							.addCreator(Student.class)
							.addCreator(Apprentice.class)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"kennisdeling",
					new FormTemplateLayoutBuilder("kennisdeling")
							.addElement(new TemplateElement("files", FileElement.class, "bestanden", null))
							.addElement(new TemplateElement(dataPointTypes.get(0).getType(), DataPointElement.class, dataPointTypes.get(0).getType(), new DataPointElementValidator()))
							.addElement(new TemplateElement(dataPointTypes.get(5).getType(), DataPointElement.class, dataPointTypes.get(5).getType(), new DataPointElementValidator()))
							.addElement(new TemplateElement("extraSkill", SelfPickDataPointTypeDataPointElement.class, "extra vaardigheid", new SelfPickDataPointTypeDataPointElementValidator(bannedList)))
							.build()
			);
		}

		{
			FormTemplate retro = this.formTemplateService.create(
					new FormTemplateBuilder("retro-feedback")
							.setReviewPolicy(ReviewPolicy.AUTOMATIC_TO_GROUP)
							.addCreator(Student.class)
							.setMaxAllowedDrafts(2)
							.addCreator(Apprentice.class)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"retro-feedback",
					new FormTemplateLayoutBuilder("retro-feedback")
							.addElement(new TemplateElement("compliment", ReviewableTextElement.class, "compliment", new TextElementValidator()))
							.addElement(new TemplateElement("improvement", ReviewableTextElement.class, "verbeterpunt", new TextElementValidator()))
							.addElement(new TemplateElement("datapoint", ReviewableDataPointTypeSelectorElement.class, "Feedback gevraagd op vaardigheid`Beste vaardigheid", null))
							.build()
			);
		}

		{
			FormTemplate ontwikkelDoel = this.formTemplateService.create(
					new FormTemplateBuilder("ontwikkeldoel")
							.setMaxAllowedDrafts(3)
							.addCreator(Student.class)
							.addCreator(Apprentice.class)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"ontwikkeldoel",
					new FormTemplateLayoutBuilder("ontwikkeldoel")
							.addElement(new TemplateElement("developmentGoalSection", SectionElement.class, "ontwikkeldoel", null))
							.addElement(new TemplateElement("developmentGoal", DevelopmentGoalOnDataPointTypeElement.class, "ontwikkeldoel", null))
							.addElement(new TemplateElement("actions", SectionElement.class, "acties", null))
							.addElement(new TemplateElement("actionOne", DevelopmentActionElement.class, "actie een", null))
							.addElement(new TemplateElement("actionTwo", DevelopmentActionElement.class, "actie twee", null))
							.addElement(new TemplateElement("actionThree", DevelopmentActionElement.class, "actie drie", null))
							.addElement(new TemplateElement("actionFour", DevelopmentActionElement.class, "actie vier", null))
							.addElement(new TemplateElement("actionFive", DevelopmentActionElement.class, "actie vijf", null))
							.build()
			);
		}

		{
			FormTemplate ontwikkelBewijs = this.formTemplateService.create(
					new FormTemplateBuilder("ontwikkelbewijs")
							.addCreator(Student.class)
							.addCreator(Apprentice.class)
							.setMaxAllowedDrafts(5)
							.setReviewPolicy(ReviewPolicy.MANUALLY_TEACHER)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"ontwikkelbewijs",
					new FormTemplateLayoutBuilder("ontwikkelbewijs")
							.addElement(new TemplateElement("files", FileElement.class, "bestanden", null))
							.addElement(new TemplateElement("explanation", TextElement.class, "Toelichting ontwikkelbewijs", new TextElementValidator()))
							.addElement(new TemplateElement("requiredSkill", SelfPickDataPointTypeDataPointElement.class, "verplicht vaardigheid", new SelfPickDataPointTypeDataPointElementValidator()))
							.addElement(new TemplateElement("optionalFields", SectionElement.class, "optioneel", null))
							.addElement(new TemplateElement("optionalSkillOne", SelfPickDataPointTypeDataPointElement.class, "extra vaardigheid 1", null))
							.addElement(new TemplateElement("optionalSkillTwo", SelfPickDataPointTypeDataPointElement.class, "extra vaardigheid 2", null))
							.build()
			);
		}

		{
			FormTemplateLayoutBuilder assessmentTemplateBuilder = new FormTemplateLayoutBuilder("assessment");
			for (String type : types) {
				assessmentTemplateBuilder.addElement(new TemplateElement(type, AssessmentElement.class, type, null));
			}
			FormTemplate assessment = this.formTemplateService.create(
					new FormTemplateBuilder("assessment")
							.addCreator(Student.class)
							.addCreator(Apprentice.class)
							.build()
			);
			this.formTemplateService.addLayoutVersionToTemplate(
					"assessment",
					assessmentTemplateBuilder.build()
			);
		}
	}

	private void fillFormDataToStudentAndApprentice(List<DataPointType> dataPointTypes, String apprenticeEmail, String studentEmail) throws NoSelfReviewException, FormIsLockedException, ReviewerUnableToGiveDegreeOfAssessmentException, OpenReviewAlreadyExistsException, InvalidFormAccessException, InvalidFormElementValue {
		for (int i = 0; i < 31; i++) {
			UUID formId = this.formTask.createForm(studentEmail, "product review");
			Map<String, Object> dto = new HashMap<>();

			Random r = new Random();

			dto.put("title", randomString());
			dto.put("description", randomString());
			dto.put("acceptCriteria", randomString());
			dto.put("qualityCriteria", randomString());

			dto.put(dataPointTypes.get(0).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(0).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(1).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(1).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(2).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(2).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(3).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(3).getType(), Progress.values()[r.nextInt(0, 3)].name());

			dto.put("competence", mainCompetences[r.nextInt(0, 5)] + "()" + subCompetences[r.nextInt(0, 5)] + "()" + r.nextInt(1,4));

			this.formTask.finishForm(studentEmail, formId, dto);

			this.reviewTask.addReviewer(studentEmail, formId, apprenticeEmail, DegreeOfAssessment.PEER);

			dto = new HashMap<>();

			dto.put(dataPointTypes.get(0).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(0).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(0).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(1).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(1).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(1).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(2).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(2).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(2).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(3).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(3).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(3).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());

			dto.put("progress" + "competence" + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put("competenceReviewer", randomString());

			this.formTask.finishFormReviewer(apprenticeEmail, formId, dto);

		}

		for (int i = 0; i < 31; i++) {
			UUID formId = this.formTask.createForm(apprenticeEmail, "product review");
			Map<String, Object> dto = new HashMap<>();

			Random r = new Random();

			dto.put("title", randomString());
			dto.put("description", randomString());
			dto.put("acceptCriteria", randomString());
			dto.put("qualityCriteria", randomString());

			dto.put(dataPointTypes.get(0).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(0).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(1).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(1).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(2).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(2).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(3).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(3).getType(), Progress.values()[r.nextInt(0, 3)].name());

			dto.put("competence", mainCompetences[r.nextInt(0, 5)] + "()" + subCompetences[r.nextInt(0, 5)] + "()" + r.nextInt(1,4));

			this.formTask.finishForm(apprenticeEmail, formId, dto);

			this.reviewTask.addReviewer(apprenticeEmail, formId, studentEmail, DegreeOfAssessment.PEER);

			dto = new HashMap<>();

			dto.put(dataPointTypes.get(0).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(0).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(0).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(1).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(1).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(1).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(2).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(2).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(2).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(3).getType() + "ComplimentReviewer", randomString());
			dto.put(dataPointTypes.get(3).getType() + "ImprovementReviewer", randomString());
			dto.put("progress" + dataPointTypes.get(3).getType() + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());

			dto.put("progress" + "competence" + "Reviewer", Progress.values()[r.nextInt(0, 3)].name());
			dto.put("competenceReviewer", randomString());

			this.formTask.finishFormReviewer(studentEmail, formId, dto);

		}

		for (int i = 0; i < 8; i++) {
			UUID formId = this.formTask.createForm(studentEmail, "product review");
			Map<String, Object> dto = new HashMap<>();

			Random r = new Random();

			dto.put("title", randomString());
			dto.put("description", randomString());
			dto.put("acceptCriteria", randomString());
			dto.put("qualityCriteria", randomString());

			dto.put(dataPointTypes.get(0).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(0).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(1).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(1).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(2).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(2).getType(), Progress.values()[r.nextInt(0, 3)].name());
			dto.put(dataPointTypes.get(3).getType(), randomString());
			dto.put("progress" + dataPointTypes.get(3).getType(), Progress.values()[r.nextInt(0, 3)].name());

			dto.put("competence", mainCompetences[r.nextInt(0, 5)] + "()" + subCompetences[r.nextInt(0, 5)] + "()" + r.nextInt(1,4));

			this.formTask.finishForm(studentEmail, formId, dto);

			this.openReviewTask.addToBoard(studentEmail, formId, "hardcode", DegreeOfAssessment.PEER);
		}

		for (int i = 0; i < 5; i++) {
			UUID formId = this.formTask.createForm(studentEmail, "daily standup");
			Map<String, Object> dto = new HashMap<>();

			Random r = new Random();

			dto.put("executedActivities", randomString());
			dto.put("learned", randomString());
			dto.put("plannedActivities", randomString());
			dto.put("questions", randomString());
			dto.put("why", randomString());

			dto.put("duration", "0" + r.nextInt(0, 10) +":" + r.nextInt(10, 60));


			dto.put("emotion", Emotion.values()[r.nextInt(0, 5)].name());

			this.formTask.finishForm(studentEmail, formId, dto);
		}

		for (int i = 0; i < 5; i++) {
			UUID formId = this.formTask.createForm(apprenticeEmail, "daily standup");
			Map<String, Object> dto = new HashMap<>();

			Random r = new Random();

			dto.put("executedActivities", randomString());
			dto.put("learned", randomString());
			dto.put("plannedActivities", randomString());
			dto.put("questions", randomString());
			dto.put("why", randomString());

			dto.put("duration", "0" + r.nextInt(0, 10) +":" + r.nextInt(10, 60));


			dto.put("emotion", Emotion.values()[r.nextInt(0, 5)].name());

			this.formTask.finishForm(apprenticeEmail, formId, dto);
		}
	}

	private void createDataPoints() {
		for (String type : types) {
			this.dataPointTypeService.create(type, "desc");
		}
	}

	private void createCompetences() {
		for (String mainCompetence : mainCompetences) {
			for (String subCompetence : subCompetences) {
				for (short i = 1; i < 4; i++) {
					service.createCompetence(
							mainCompetence,
							subCompetence,
							i,
							"""
									Bouwen en beschikbaar stellen van een softwaresysteem dat bestaat uit meerdere subsystemen, hierbij gebruikmakend van bestaande componenten.

									Integreren van softwarecomponenten in een bestaand systeem, waarbij o.a. de integriteit, veiligheid en systeemprestaties bewaakt worden.

									Uitvoeren van, monitoren van en rapporteren over unit-, integratie-, regressie-, en systeemtesten, met aandacht voor security-aspecten.""");
				}
			}
		}
	}

	private void createBadges() {
		createBadgesInGroup("Operating Systems", new String[]{"windows", "linux", "macos", "android"});
		createBadgesInGroup("Programming Languages", new String[]{"python", "R", "javascript", "html", "css", "git", "sql", "java", "rust", "swift", "microsoft java", "ruby", "kotlin", "nosql", "typescript", "sass", "go", "latex", "c++", "c", "php"});
		createBadgesInGroup("Frameworks", new String[]{"react", "vue", "graphql", "flutter", "fastapi", "neo4j", "flask", "jsx", "spring boot", "angular"});
		createBadgesInGroup("Software", new String[]{"github", "docker", "cloud", "internet", "firebase", "kubernetes", "azure", "heroku", "npm"});
		createBadgesInGroup("vaardigheden", new String[]{"encryptie", "jetbrains", "security", "onderzoek", "gitlab", "gaming", "node", "azure devops", "hbo-i"});
		createBadgesInGroup("Overig", new String[]{"open innovation", "stage"});

		// if groupName of user is null it can only be assigned by admin
		createBadgesInGroup(null, new String[]{"project pear", "scorion"});
	}


	private String randomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	private void createBadgesInGroup(String groupName, String[] badgeNames) {
		for (String badgeName : badgeNames) {
			this.badgeTask.createBadge(badgeName, groupName);
		}
	}
}
