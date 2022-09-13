export class Skill {
    static JUISTE_KENNIS_ONTWIKKELEN = new Skill("Juiste kennis ontwikkelen", "juisteKennis");
    static KWALATIEF_PRODUCT_MAKEN = new Skill("Kwalatief product maken", "kwalatiefProductMaken");
    static CREATIEF_WERKEN = new Skill("Creatief werken", "creatiefWerken");
    static KRITISCH_OORDELEN = new Skill("Kritisch oordelen", "kritischOordelen");
    static SAMENWERKEN = new Skill("Samenwerken", "samenwerken");
    static BOODSCHAP_OVERBRENGEN = new Skill("Boodschap overbrengen", "boodschapOverbrengen");
    static PLANNEN = new Skill("Plannen", "plannen");
    static AANPASSINGSVERMOGEN = new Skill("Aanpassingsvermogen", "aanpassingsvermogen");
    static PROACTIEF_HANDELEN = new Skill("Pro-actief handelen", "proactiefHandelen");
    static REFLECTEREN = new Skill("Reflecteren", "reflecteren");
    
    constructor(label, dtoName) {
        this.label = label;
        this.dtoName = dtoName;
    }
}

export class Progress {
    static IN_ONTWIKKELING = "In ontwikkeling";
    static OP_NIVEAU = "Op niveau";
    static BOVEN_NIVEAU = "Boven niveau";
}

export class ReviewTypes {
    static PEER = "Peer";
    static APPRENTICE = "Apprentice";
    static GUILD = "Gilde";
    static GUILD_MASTER = "Gilde meester";
    static EXTERN = "Extern";
}

export class InputFields {
    static ASSESSMENT = Object.freeze(new InputFields());
    static SECTION = new InputFields();
    static TEXT = new InputFields();
    static CRITERIA = new InputFields();
    static HBOI = new InputFields();
    static REVIEW_TEXT = new InputFields();
    static RETRO_SKILL = new InputFields();
    static EXTRA_SKILL_RADIO = new InputFields();
    static EXTRA_SKILL = new InputFields();
    static EMOTION = new InputFields();
    static TIME = new InputFields();
}

export class Form {
    static Assessment = new Form("assessment", "assessment", Object.keys(Skill).map(skill => {return {skill: skill, label: Skill[skill].label, name: Skill[skill].dtoName, type: InputFields.ASSESSMENT}}), true);
    static DevelopmentGoals = new Form("ontwikkeldoelen", "developmentgoals", [], false);
    static ProductReview = new Form("product review", "productreview", [
        {label:"product informatie", type: InputFields.SECTION},
        {label:"titel", name:"title", type: InputFields.TEXT},
        {label:"omschrijving", name:"description", type: InputFields.TEXT},
        {label:"acceptatie criteria", name:"acceptCriteria", type: InputFields.TEXT},
        {label:"kwaliteits criteria", name:"qualityCriteria", type: InputFields.TEXT},
        {label:"skills", type: InputFields.SECTION},
        {label:"Juiste kennis ontwikkelen", name:"juisteKennis", type: InputFields.CRITERIA},
        {label:"Kwalatief product maken", name:"kwalatiefProductMaken", type: InputFields.CRITERIA},
        {label:"Creatief werken", name:"creatiefWerken", type: InputFields.CRITERIA},
        {label:"Kritisch oordelen", name:"kritischOordelen", type: InputFields.CRITERIA},
        {label:"HBO-I compentencie", type: InputFields.SECTION},
        {label:"", name:"HBOICompetence", type: InputFields.HBOI},
    ], true);
    static RetroFeedback = new Form("retro-feedback", "retrofeedback", [
        {label: "compliment", name:"compliment", type: InputFields.REVIEW_TEXT},
        {label: "verbeterpunt", name:"improvementPoint", type: InputFields.REVIEW_TEXT},
        {label: "Waarom voel je je zo?", name:"feedbackSkill", type: InputFields.RETRO_SKILL},
    ], true, false);
    static KnowledgeSharing = new Form("kennisdeling", "knowledgesharing", [
        {label: "Waarom voel je je zo?", name:"why", type: InputFields.EXTRA_SKILL_RADIO},
        {label:"Juiste kennis ontwikkelen", name:"juisteKennis", type: InputFields.CRITERIA},
        {label:"Boodschap overbrengen", name:"boodschapOverbrengen", type: InputFields.CRITERIA},
        {type: InputFields.EXTRA_SKILL}
    ], true);
    static DevelopmentProof = new Form("ontwikkelbewijs", "developmentproof", [], false);
    static DailyStandup = new Form("daily standup", "dailystandup", [
        {label: "Check-in", name:"emotion", type: InputFields.SECTION},
        {label: "Hoe voel je vandaag?", type: InputFields.EMOTION},
        {label: "Waarom voel je je zo?", name:"why", type: InputFields.TEXT},
        {label: "Wat ga je vandaag doen?", name:"plannedActivities", type: InputFields.TEXT},
        {label: "Welke eventuele hulpvragen heb je?", name:"questions", type: InputFields.TEXT},
        {label: "Check-out", type: InputFields.SECTION},
        {label: "Wat heb je vandaag gedaan?", name:"executedActivities", type: InputFields.TEXT},
        {label: "Hoeveel uren en minuten heb je vandaag gewerkt?", name:"qwerty", type: InputFields.TIME},
        {label: "Wat heb je vandaag geleerd?", name:"learned", type: InputFields.TEXT},
    ], false);

    constructor(name, apiEndpoint, elements, reviewable = false, addReviewerManually = true) {
        this.name = name;
        this.apiEndpoint = apiEndpoint;
        this.elements = elements;
        this.reviewable = reviewable;
        this.addReviewerManually = addReviewerManually;
    }
}