<template>
  <div style="width: 100vw">
    <overlay-div ref="overlay">
      <DoneForm :form-prop-info="this.overlayData.formType" :form-id="this.overlayData.id"></DoneForm>
    </overlay-div>
    <form>
      <h1 class="form-title">{{ this.meta.formTemplate.templateName }}</h1>
      <div v-for="templateElement in this.meta.formTemplate.elements" v-bind:key="templateElement" class="form-section">
        <template v-if="templateElement.type === 'TextElement'">
          <h3>{{templateElement.label}}</h3>
          <div :data-betsoc-label="templateElement.name"></div>
        </template>
        <template v-if="templateElement.type === 'ReviewableTextElement'">
          <div :data-betsoc-label="templateElement.name"></div>
          <text-box :label="templateElement.label" :name="templateElement.name + 'Reviewer'" @update-form="catchEvent"></text-box>
        </template>
        <template v-else-if="templateElement.type === 'SectionElement'">
          <h2 class="section">{{ templateElement.label }}</h2>
        </template>
        <template v-else-if="templateElement.type === 'DataPointElement'">
          <h3>{{templateElement.label}}</h3>
          <progress-radio-input :name="templateElement.name" disabled></progress-radio-input>
          <div :data-betsoc-label="templateElement.name"></div>
          <criteria-reviewer :label="templateElement.label" :name="templateElement.name" @update-form="catchEvent"></criteria-reviewer>
        </template>
        <template v-else-if="templateElement.type === 'NotApplicableDataPointElement'">
          <not-applicable-data-point-reviewer :label="templateElement.label" :name="templateElement.name"></not-applicable-data-point-reviewer>
        </template>
        <template v-else-if="templateElement.type === 'CompetenceElement'">
          <p class="label" :data-betsoc-label="templateElement.name"></p>
          <p :data-betsoc-label="templateElement.name + 'Description'"></p>
          <criteria-student label="" :name="templateElement.name + 'Reviewer'" @update-form="catchEvent"></criteria-student>
        </template>
        <template v-else-if="templateElement.type === InputFields.ASSESSMENT">
          <h2 class="section">{{ templateElement.label }}</h2>
          <progress-radio-input :name="templateElement.name + 'Assessment'" disabled></progress-radio-input>
          <div :data-betsoc-label="templateElement.name + 'Assessment'"></div>
          <assessment-progress
              :measurement-points="measurementPoints[templateElement.skill]"
              v-bind:key="measurementPoints[templateElement.skill]"
              @show-overlay="(e) => this.showFormOverlay(e)"></assessment-progress>
          <criteria-student label="" :name="templateElement.name + 'AssessmentReviewer'" @update-form="catchEvent"></criteria-student>
        </template>
        <template v-else-if="extraSkill && templateElement.type === InputFields.EXTRA_SKILL">
          <div v-if="extraSkill" id="extraSkill">
            <h3>{{extraSkill.label}}</h3>
            <progress-radio-input name="extraSkill" disabled></progress-radio-input>
            <div data-betsoc-label="extraSkill"></div>
            <criteria-reviewer :label="extraSkill.label" name="extraSkill" @update-form="catchEvent"></criteria-reviewer>
          </div>
        </template>
        <template v-if="templateElement.type === InputFields.REVIEW_TEXT">
          <div :data-betsoc-label="templateElement.name"></div>
          <br>
          <text-box :label="templateElement.label" :name="templateElement.name + 'Reviewer'" @update-form="catchEvent"></text-box>
        </template>
        <template v-else-if="templateElement.type === InputFields.RETRO_SKILL">
          <skill-radio label="Feedback gevraagd op vaardigheid" name="feedbackSkill" disabled></skill-radio>
          <skill-radio label="Beste skill" name="feedbackSkillReviewer"></skill-radio>
        </template>
        <br>
      </div>
      <!--    <div v-if="this.meta.formType === form.Assessment">-->
      <!--      <h2 class="section">eindbeoordeling</h2>-->
      <!--      <input type="number" step="0.1" min="1" max="10" name="grade">-->
      <!--      <text-box label="toelichting" name="explanationGrade"></text-box>-->
      <!--    </div>-->
      <div class="form-buttons">
        <button type="button" @click="this.rejectReview">weigeren</button>
        <button type="button" @click="this.updateForm(false, true)">tussentijds opslaan</button>
        <button type="button" @click="this.updateForm(true, false)">submit</button>
      </div>
    </form>
  </div>

</template>

<script>
import CriteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
import {markdown} from "@/utils/markdown";
import {InputFields, Skill} from "@/utils/constants";
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import TextBox from "@/modules/formelements/components/TextBox.vue";
import OverlayDiv from "@/components/OverlayDiv.vue";
import DoneForm from "@/modules/formelements/views/DoneForm.vue";
import SkillRadio from "@/modules/formelements/components/SkillRadio.vue";
import NotApplicableDataPointReviewer from "@/modules/formelements/components/NotApplicableDataPointReviewer.vue";
import fetchAuth from "@/utils/authrequest";


export default {
  name: "FillFormReviewer",
  components: {
    NotApplicableDataPointReviewer,
    SkillRadio, OverlayDiv, DoneForm, TextBox, AssessmentProgress, CriteriaStudent, CriteriaReviewer, ProgressRadioInput},
  data() {
    return {
      meta: {formTemplate: {templateName:'', elements: []}},
      extraSkill: undefined,
      InputFields: InputFields,
      measurementPoints: {},
      overlayData: {},
    }
  },
  beforeMount() {
    document.addEventListener("keydown", this.keyDown);
    document.addEventListener("keyup", this.keyUp);

    this.setupForm(this.$route.params.id);

    // if (this.$route.meta.formType === Form.ProductReview)this.fetchHBOICompetence();
    // if (this.$route.meta.formType === Form.Assessment) this.fetchProgress();
  },
  methods: {
    async setupForm(id) {
      const meta = this.fetchMeta(id);
      const data = this.fetchForm(id);
      const dataReviewer = this.fetchFormReviewer(id);

      const result = await Promise.all([meta, data, dataReviewer]);

      this.meta = result[0];

      const jsonData = {...result[1], ...result[2]};

      await this.$nextTick(async () => {
        const names = Object.keys(jsonData)
        for (const name of names) {
          let inputField = document.querySelector(`[data-betsoc-label="${name}"]`);
          if (inputField == null || jsonData[name] == null) continue;
          switch (inputField.constructor) {
            case HTMLInputElement: {
              if (inputField.type === "radio") {
                inputField = document.querySelector(`input[data-betsoc-label="${name}"][value="${jsonData[name]}"]`);

                inputField.checked = true;
              }
              break;
            }
            case HTMLDivElement: {
              inputField.innerHTML = markdown.render(jsonData[name]);
              break;
            }
            case HTMLTextAreaElement:  {
              inputField.innerText = jsonData[name];
              break;
            }
            case HTMLParagraphElement:  {
              inputField.innerText = jsonData[name];
              break;
            }
          }
          if (inputField.onresize) await this.$nextTick(() => inputField.onresize.call(this));
        }
        return jsonData;

      })

    },
    fetchMeta(id) {
      return fetchAuth(`/api/form/${id}/meta`).then(response => response.json())
    },
    fetchForm(id) {
      return fetchAuth(`/api/form/${id}`)
          .then(response => response.json())
          .then(json => {
            // if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    fetchFormReviewer(id) {
      return fetchAuth(`/api/form/${id}/review`)
          .then(response => response.json())
          .then(json => {
            // if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    processExtraInformation(json) {
      this.extraSkill = {label:Skill[json["skill"]], name:"extraSkill"};
    },
    updateForm(submit = false, notify = true) {
      fetchAuth(`/api/form/${this.$route.params.id}/review`, {
        method: submit ? "post" : "put",
        body: this.serializeAndJsonify(document.querySelector("form")),
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      }).then(response => response.status).then(status => {
        // if (status === 304) window.close();
        if (status === 200 && submit) {
          this.$router.push({path:"/reviews"})
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
    rejectReview() {
      fetchAuth(`/api/review/${this.$route.params.id}`, {method: "DELETE"}).then(() => this.$router.push({path:"/reviews"}))
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
    fetchProgress() {
      fetchAuth("/api/progress")
          .then(response => response.json())
          .then(json => {
            this.measurementPoints = json;
          })
    },
    showFormOverlay(e) {
      this.overlayData = {formType:this.form.ProductReview, id:e.id}
      this.$refs.overlay.showOverlay();
    },
  }
}
</script>

<style src="@/assets/css/form.less" />

<!--<style scoped lang="less">-->
<!--.section {-->
<!--  background-color: #42b983;-->
<!--}-->
<!--.form-buttons {-->
<!--  margin: 2em 0;-->

<!--  button {-->
<!--    &:first-child {-->
<!--      margin-right: 1em;-->
<!--    }-->
<!--    &:last-child {-->
<!--      margin-left: 1em;-->
<!--    }-->
<!--  }-->
<!--}-->
<!--p.label {-->
<!--  font-weight: bold;-->
<!--  font-size: 1.25em;-->
<!--}-->

<!--p.required-form-element::after {-->
<!--  content: "*";-->
<!--  color: red;-->
<!--}-->
<!--</style>-->