import DoneForm from "@/modules/formelements/views/DoneForm.vue";
import OverlayDiv from "@/components/OverlayDiv.vue";
import TextBox from "@/modules/formelements/components/TextBox.vue";
import NotApplicableDataPointReviewer from "@/modules/formelements/components/NotApplicableDataPointReviewer.vue";
import TimeDurationInput from "@/modules/formelements/components/TimeDurationInput.vue";
import EmotionGroupInput from "@/modules/formelements/components/EmotionGroupInput.vue";
import SectionElement from "@/modules/formelements/components/SectionElement.vue";
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";
import SelfPickableDataPoint from "@/modules/formelements/components/SelfPickableDataPoint.vue";
import DataPointTypeRadioInput from "@/modules/formelements/components/DataPointTypeRadioInput.vue";
import Competence from "@/modules/formelements/components/Competence.vue";
import DevelopmentGoal from "@/modules/formelements/components/DevelopmentGoal.vue";
import Action from "@/modules/formelements/components/Action.vue";
import AssessmentInput from "@/modules/formelements/components/AssessmentInput.vue";
import NotApplicableDataPointStudent from "@/modules/formelements/components/NotApplicableDataPointStudent.vue";
import CriteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import TextBoxDone from "@/modules/formelements/components/TextBoxDone.vue";
import CompetenceReviewer from "@/modules/formelements/components/CompetenceReviewer.vue";
import CriteriaDone from "@/modules/formelements/components/CriteriaDone.vue";
import NotApplicableDataPointDone from "@/modules/formelements/components/NotApplicableDataPointDone.vue";
import CompetenceDone from "@/modules/formelements/components/CompetenceDone.vue";
import EmotionGroupInputDone from "@/modules/formelements/components/EmotionGroupInputDone.vue";
import SelfPickableDataPointReviewer from "@/modules/formelements/components/SelfPickableDataPointReviewer.vue";
import FileElement from "@/modules/formelements/components/FileElement.vue";
import SelfPickableDataPointDone from "@/modules/formelements/components/SelfPickableDataPointDone.vue";
import ReviewableDataPointTypeSelector from "@/modules/formelements/components/ReviewableDataPointTypeSelector.vue";
import ReviewableTextBoxReview from "@/modules/formelements/components/ReviewableTextBoxReview.vue";
import ReviewableDataPointTypeSelectorReview from "@/modules/formelements/components/ReviewableDataPointTypeSelectorReview.vue";
import ReviewableTextBoxDone from "@/modules/formelements/components/ReviewableTextBoxDone.vue";
import ReviewableDataPointTypeSelectorDone from "@/modules/formelements/components/ReviewableDataPointTypeSelectorDone.vue";
import FileElementReviewer from "@/modules/formelements/components/FileElementReviewer.vue";

const elements = {
    TextElement: NotApplicableDataPointReviewer
}

export const fillFormElements = {
    DoneForm,
    OverlayDiv,
    TextElement: TextBox,
    ReviewableTextElement: TextBox,
    DurationElement: TimeDurationInput,
    EmotionElement: EmotionGroupInput,
    SectionElement,
    DataPointElement: CriteriaStudent,
    NotApplicableDataPointElement: NotApplicableDataPointStudent,
    SelfPickDataPointTypeDataPointElement: SelfPickableDataPoint,
    ReviewableDataPointTypeSelectorElement: ReviewableDataPointTypeSelector,
    CompetenceElement: Competence,
    DevelopmentGoalOnDataPointTypeElement: DevelopmentGoal,
    DevelopmentActionElement: Action,
    AssessmentElement: AssessmentInput,
    FileElement,
}

export const reviewerElements = {
    DoneForm,
    OverlayDiv,
    TextElement: TextBoxDone,
    ReviewableTextElement: ReviewableTextBoxReview,
    DurationElement: TimeDurationInput,
    EmotionElement: EmotionGroupInput,
    SectionElement,
    DataPointElement: CriteriaReviewer,
    NotApplicableDataPointElement: NotApplicableDataPointReviewer,
    SelfPickDataPointTypeDataPointElement: SelfPickableDataPointReviewer,
    ReviewableDataPointTypeSelectorElement: ReviewableDataPointTypeSelectorReview,
    CompetenceElement: CompetenceReviewer,
    DevelopmentGoalOnDataPointTypeElement: DevelopmentGoal,
    DevelopmentActionElement: Action,
    AssessmentElement: AssessmentInput,
    FileElement: FileElementReviewer,
}

export const doneElements = {
    DoneForm,
    OverlayDiv,
    TextElement: TextBoxDone,
    ReviewableTextElement: ReviewableTextBoxDone,
    DurationElement: TextBoxDone,
    EmotionElement: EmotionGroupInputDone,
    SectionElement,
    DataPointElement: CriteriaDone,
    NotApplicableDataPointElement: NotApplicableDataPointDone,
    SelfPickDataPointTypeDataPointElement: SelfPickableDataPointDone,
    ReviewableDataPointTypeSelectorElement: ReviewableDataPointTypeSelectorDone,
    CompetenceElement: CompetenceDone,
    DevelopmentGoalOnDataPointTypeElement: DevelopmentGoal,
    DevelopmentActionElement: Action,
    AssessmentElement: AssessmentInput,
}