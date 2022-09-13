<template>
<!--  <div>-->
<!--    <p> {{ meta }} </p>-->
<!--  </div>-->
  <div style="width: 100vw">
    <overlay-div ref="overlay">
      <DoneForm :form-prop-info="this.overlayData.formType" :form-id="this.overlayData.id"></DoneForm>
    </overlay-div>
    <form @change="testFormChange" @submit="submitFromRefactor" action="form-handler">
      <h1 class="form-title" contenteditable aria-label="form title" @input="changeName">{{ this.meta.name }}</h1>
      <div v-for="templateElement in this.meta.formTemplate.elements" v-bind:key="templateElement" class="form-section">
        <template v-if="templateElement.type === 'TextElement' || templateElement.type === 'ReviewableTextElement'">
          <text-box :label="templateElement.label" :name="templateElement.name" @update-form="catchEvent"></text-box>
        </template>
        <template v-else-if="templateElement.type === 'DurationElement'">
          <time-duration-input :label="templateElement.label" :name="templateElement.name"></time-duration-input>
        </template>
        <template v-else-if="templateElement.type === 'EmotionElement'">
          <p class="label required-form-element">
            {{ templateElement.label }}
          </p>
          <div style="display: inline-flex">
            <br>
            <emotion-radio-input emotion="ZEER_SLECHT" @change="catchEvent"></emotion-radio-input>
            <emotion-radio-input emotion="SLECHT" @change="catchEvent"></emotion-radio-input>
            <emotion-radio-input emotion="NEUTRAAL" @change="catchEvent"></emotion-radio-input>
            <emotion-radio-input emotion="GOED" @change="catchEvent"></emotion-radio-input>
            <emotion-radio-input emotion="ZEER_GOED" @change="catchEvent"></emotion-radio-input>
          </div>
        </template>
        <template v-else-if="templateElement.type === 'SectionElement'">
          <h2 class="section">{{ templateElement.label }}</h2>
        </template>
        <template v-else-if="templateElement.type === 'DataPointElement'">
          <criteria-student :label="templateElement.label" :name="templateElement.name" @update-form="catchEvent"></criteria-student>
        </template>
        <template v-else-if="templateElement.type === 'NotApplicableDataPointElement'">
          <criteria-student :label="templateElement.label" notApplicable :name="templateElement.name" @update-form="catchEvent"></criteria-student>
        </template>
        <template v-else-if="templateElement.type === InputFields.EXTRA_SKILL_RADIO">
          <!--        <div style="display: grid" ref="extraSkillRadioButtons">-->
          <!--          <button @click="this.resetRadioButtonsExtraSkill" type="button">rest radio</button>-->
          <!--          <label v-for="key in Object.keys(this.skills)" v-bind:key="key"><input type="radio" name="skill" :value="key" @change="this.extraSkill = this.skills[key].label">{{this.skills[key].label}}</label>-->
          <!--          <label><input type="radio" name="skill" value="undefined" @change="this.extraSkill = null">none</label>-->
          <!--        </div>-->
        </template>
        <template v-else-if="templateElement.type === 'SelfPickDataPointTypeDataPointElement'">
          <p class="label">{{ templateElement.label }}</p>
          <self-pickable-data-point :name="templateElement.name" :pickableOptions="this.dataTypePickableOptions"></self-pickable-data-point>
        </template>
        <template v-else-if="templateElement.type === 'ReviewableDataPointTypeSelectorElement'">
          <p class="label">{{ templateElement.label }}</p>
          <data-point-type-radio-input :name="templateElement.name" :dataPointTypes="this.dataTypePickableOptions"></data-point-type-radio-input>
        </template>
        <template v-else-if="templateElement.type === 'CompetenceElement'">
          <competence :name="templateElement.name" :label="templateElement.label"></competence>
        </template>
        <template v-else-if="templateElement.type === 'DevelopmentGoalOnDataPointTypeElement'">
          <development-goal :pickableOptions="this.dataTypePickableOptions" :name="templateElement.name"/>
        </template>
        <template v-else-if="templateElement.type === 'DevelopmentActionElement'">
          <action :name="templateElement.name"></action>
        </template>
        <template  v-else-if="templateElement.type === 'AssessmentElement'">
          <h2 class="section">{{ templateElement.label }}</h2>
          <assessment-progress
              :measurement-points="measurementPoints[templateElement.name]"
              v-bind:key="measurementPoints[templateElement.name]"
              @show-overlay="(e) => this.showFormOverlay(e)"></assessment-progress>
          <criteria-student label="" :name="templateElement.name + 'Assessment'" @update-form="catchEvent"></criteria-student>
        </template>
        <template v-else-if="templateElement.type === InputFields.RETRO_SKILL">
          <skill-radio label="Feedback gevraagd op vaardigheid" :name="templateElement.name"></skill-radio>
        </template>
        <br v-if="templateElement.type !== 'SectionElement'">
      </div>
      <div class="form-buttons">
        <button type="button" @click="this.updateForm(false, true)">tussentijds opslaan</button>
        <button type="button" @click="this.updateForm(true, false)">submit</button>
      </div>
    </form>
  </div>

</template>

<script>
import EmotionRadioInput from "@/modules/formelements/components/EmotionRadioInput.vue";
import TimeDurationInput from "@/modules/formelements/components/TimeDurationInput.vue";
import TextBox from "@/modules/formelements/components/TextBox.vue";
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";
import {Form, InputFields, Skill} from "@/utils/constants";
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import DoneForm from "@/modules/formelements/views/DoneForm.vue";
import OverlayDiv from "@/components/OverlayDiv.vue";
import SkillRadio from "@/modules/formelements/components/SkillRadio.vue";
import Competence from "@/modules/formelements/components/Competence.vue";
import DataPointTypeRadioInput from "@/modules/formelements/components/DataPointTypeRadioInput.vue";
import SelfPickableDataPoint from "@/modules/formelements/components/SelfPickableDataPoint.vue";
import {markdown} from "@/utils/markdown";
import DevelopmentGoal from "@/modules/formelements/components/DevelopmentGoal.vue";
import Action from "@/modules/formelements/components/Action.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "FillForm",
  components: {
    Action,
    DevelopmentGoal,
    DataPointTypeRadioInput,
    Competence,
    SkillRadio, OverlayDiv, DoneForm, AssessmentProgress, EmotionRadioInput, TimeDurationInput, TextBox, CriteriaStudent, SelfPickableDataPoint},
  data() {
    return {
      meta: {formTemplate: {templateName:'', elements: [], reviewPolicy: undefined}},
      extraInformation: {},
      previousChange: Date.now(),
      isCtrlPressed: false,
      skills: Skill,
      InputFields: InputFields,
      extraSkill: undefined,
      momento: new Map(),
      hboi: [],
      measurementPoints: {},
      formEnum: Form,
      overlayData: {},
      dataTypePickableOptions: [],
    }
  },
  beforeMount() {
    this.setupForm(this.$route.params.id);
  },
  mounted() {
    document.addEventListener("keydown", this.keyDown);
    document.addEventListener("keyup", this.keyUp);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.keyDown);
    document.removeEventListener('keyup', this.keyUp);
  },
  // computed: {
  //   showExtraSkill() {
  //
  //     if (!this.$refs.extraSkillRadioButtons) return false;
  //
  //     return [...this.$refs.extraSkillRadioButtons[0].children].filter(htmlElement => htmlElement instanceof HTMLLabelElement).map(label => label.control.checked).includes(true);
  //   },
  //   test(skill) {
  //     if (!this.measurementPoints[skill]) return [];
  //     return this.measurementPoints[skill];
  //   }
  // },
  methods: {
    async setupForm(id) {
      const meta = this.fetchMeta(id);
      const data = this.fetchForm(id);

      const result = await Promise.all([data, meta]);

      Object.values(result[1].formTemplate.elements)
          .filter(e => e.label.includes('`'))
          .forEach(e => e.label = e.label.split("`")[0]);


      this.meta = result[1];

      await this.$nextTick(() => {});

      const elementTypes = Object.values(this.meta.formTemplate.elements).map(element => element.type);
      const beforeFetches = [];

      if (elementTypes.filter(type => ['SelfPickDataPointTypeDataPointElement', 'ReviewableDataPointTypeSelectorElement', 'DevelopmentGoalOnDataPointTypeElement'].includes(type)).length > 0) {
        beforeFetches.push(fetchAuth("/api/datapointtype")
            .then(response => response.json())
            .then(json => {
              this.dataTypePickableOptions = json;
            }))
      }

      if (elementTypes.includes('AssessmentElement')) {
        beforeFetches.push(fetchAuth("/api/progress")
            .then(response => response.json())
            .then(json => {
              this.measurementPoints = json;
            }))
      }

      await Promise.all(beforeFetches);
      await this.$nextTick(() => {});

      const jsonData = result[0]

      await this.$nextTick(async () => {
        for (const name of Object.keys(jsonData)) {
          let inputField = document.querySelector(`[data-betsoc-label="${name}"]`)
          if (inputField == null || jsonData[name] == null) continue;
          if (inputField.type === "radio") {
            inputField = document.querySelector(`input[data-betsoc-label="${name}"][value="${jsonData[name]}"]`)
            inputField.checked = true;
          } else {
            inputField.value = jsonData[name]
            if (inputField instanceof HTMLDivElement) {
              inputField.innerHTML = markdown.render(jsonData[name]);
            }
          }
          if (inputField.onresize) await this.$nextTick(() => inputField.onresize.call(this))
        }
      })
    },
    fetchMeta(id) {
      return fetchAuth(`/api/form/${id}/meta`).then(response => response.json())
    },
    fetchForm(id) {
      return fetchAuth(`/api/form/${id}`)
          .then(response => response.json())
          .then(json => {
            if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    fetchHBOICompetence() {
      return fetchAuth("/api/hboicompetence")
          .then(response => response.json())
          .then(json => {
            this.hboi = json.map(competence => {return {label:`${competence.mainCompetence} ${competence.subCompetence} ${competence.level}`, description: competence.description};});
          })
    },
    changeHBOIDescription(option) {
      document.querySelector('#HBOIDescription').innerText = option.target.selectedOptions[0].__vnode.props.description;
    },
    processExtraInformation(json) {
      this.momento.set(json["skill"], {
        radioValue: json.progressExtraSkill,
        textValue: json.extraSkill,
      })
      this.extraSkill = this.skills[json["skill"]].label;
    },
    updateForm(submit = false, notify = true) {
      fetchAuth(`/api/form/${this.$route.params.id}`, {
        method: submit ? "post" : "put",
        body: this.serializeAndJsonify(document.querySelector("form")),
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      }).then(response => response.status).then(status => {

        // if (status === 304) window.close();
        if (status === 200 && submit) {
          if (this.meta.formTemplate.reviewPolicy !== "AUTOMATIC_TO_GROUP" && this.meta.formTemplate.reviewPolicy !== null) {
            this.$router.push({ path: `/form/reviewer/${this.$route.params.id}/${this.meta.formTemplate.reviewPolicy}`})
          } else {
            this.$router.push({path:"/home"})
          }
        }
        if (!submit && notify) {
          if (status === 200) {
            window.createNotification("success", "Form is saved.");
          } else {
            window.createNotification("error", "failed to save.");
          }
        }
      })
    },
    serializeAndJsonify(form) {
      let obj = {};
      let formData = new FormData(form);
      for (const key of formData.keys()) {
        if (formData.get(key) === 'undefined') continue;
        obj[key] = formData.get(key);
      }
      return JSON.stringify(obj);
    },
    catchEvent() {
      this.previousChange = Date.now();
      setTimeout(() => {
          if (Date.now() - this.previousChange >= 5000) {
            this.updateForm(false, false);
            this.previousChange = Date.now();
          }
        }, 5000)
    },
    keyDown(e) {
      if(e.keyCode === 17) this.isCtrlPressed = true;
      if(e.keyCode === 83 && this.isCtrlPressed) {
        this.updateForm(false, true);
      }
    },
    keyUp(e) {
      if(e.keyCode === 17) this.isCtrlPressed = true;
    },
    resetRadioButtonsExtraSkill() {
      document.querySelector(`input[name="skill"]:checked`).checked = false;
      this.extraSkill = undefined;
    },
    insertExtraSkillToMomento(from) {
      let checkedRadio = document.querySelector(`input[name$='ExtraSkill']:checked`)
      let textValue = document.querySelector(`[name='extraSkill']`)
      this.momento.set(from, {
        radioValue: checkedRadio != null ? checkedRadio.value : null,
        textValue: textValue.value,
      })
    },
    clearExtraSkillToMomento() {
      let textValue = document.querySelector(`[name='extraSkill']`)
      const radioInput = document.querySelector(`input[name$='ExtraSkill']:checked`)
      if (radioInput) radioInput.checked = false;
      if(textValue != null) textValue.value = "";
    },
    insertMomentoToCriteria(to) {
      const momentoEntry = this.momento.get(to);
      if (!momentoEntry) return;
      const radioInput = document.querySelector(`input[name$='ExtraSkill'][value='${momentoEntry.radioValue}']`)
      if (radioInput) radioInput.checked = true;
      const textBox = document.querySelector(`[name='extraSkill']`);
      if (textBox) {
        textBox.value = momentoEntry.textValue;
        textBox.onresize.call(this);
      }
    },
    fetchProgress() {
      fetchAuth("/api/progress")
          .then(response => response.json())
          .then(json => {
            this.measurementPoints = json;
            // for (let jsonKey in json) {
            //   this.measurementPoints.set(jsonKey, json[jsonKey]);
            // }

            // this.$forceUpdate();
          })
    },
    showFormOverlay(e) {
      this.overlayData = {formType:this.formEnum.ProductReview, id:e.id}
      this.$refs.overlay.showOverlay();
    },
    testFormChange() {
      this.previousChange = Date.now();
      setTimeout(() => {
        if (Date.now() - this.previousChange >= 5000) {
          this.updateForm(false, false);
          this.previousChange = Date.now();
        }
      }, 5000)
    },
    submitFromRefactor(e) {
      e.preventDefault();

    },
    changeName(e) {

      this.previousChange = Date.now();
      setTimeout(() => {
        if (Date.now() - this.previousChange >= 2000) {
          fetchAuth(`/api/form/${this.$route.params.id}/name/${e.target.textContent}`, {method: 'PATCH'})
          this.previousChange = Date.now();
        }
      }, 2000)
    },
  },
  watch: {
    extraSkill(to, from) {

      // put in momento
      if (from) {
        this.insertExtraSkillToMomento(from);
      }
      // clear extra form part
      this.clearExtraSkillToMomento();
      // insert
      this.insertMomentoToCriteria(to);
      // happens on updated life-cycle
    }
  }
}
</script>

<style src="@/assets/css/form.less" />