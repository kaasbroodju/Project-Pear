<template>
  <div class="done-form-wrapper">
    <overlay-div ref="overlay">
      <DoneForm :form-prop-info="this.overlayData.formType" :form-id="this.overlayData.id"></DoneForm>
    </overlay-div>
    <button id="pdfButton" type="button" @click="downloadAsPDF">download as pdf</button>
    <h1 class="form-title">{{ this.meta.formTemplate.templateName }}</h1>
    <div v-for="templateElement in this.meta.formTemplate.elements" v-bind:key="templateElement" class="form-section">
      <template v-if="templateElement.type === 'TextElement'">
        <h3>{{templateElement.label}}</h3>
        <div :data-betsoc-label="templateElement.name"></div>
      </template>
      <template v-else-if="templateElement.type === 'DurationElement'">
        <h3>{{templateElement.label}}</h3>
        <p :data-betsoc-label="templateElement.name"></p>
        <!--      <time-duration-input :label="templateElement.label" :name="templateElement.name" disable></time-duration-input>-->
      </template>
      <template v-else-if="templateElement.type === 'EmotionElement'">
        <div style="display: inline-flex">
          <label>
            {{ templateElement.label }}
            <br>
            <emotion-radio-input emotion="ZEER_SLECHT" disable></emotion-radio-input>
            <emotion-radio-input emotion="SLECHT" disable></emotion-radio-input>
            <emotion-radio-input emotion="NEUTRAAL" disable></emotion-radio-input>
            <emotion-radio-input emotion="GOED" disable></emotion-radio-input>
            <emotion-radio-input emotion="ZEER_GOED" disable></emotion-radio-input>
          </label>
        </div>
      </template>
      <template v-else-if="templateElement.type === 'SectionElement'">
        <h2 class="section">{{ templateElement.label }}</h2>
      </template>
      <template v-else-if="templateElement.type === 'DataPointElement'">
        <h3 class="section">{{templateElement.label}}</h3>
        <progress-radio-input :name="templateElement.name" disabled></progress-radio-input>
        <h4>toelichting</h4>
        <div :data-betsoc-label="templateElement.name"></div>
        <hr>
        <progress-radio-input :name="templateElement.name + 'Reviewer'" disabled></progress-radio-input>
        <h4>complimenten</h4>
        <div :data-betsoc-label="templateElement.name + 'ComplimentReviewer'"></div>
        <h4>verbeterpunten</h4>
        <div :data-betsoc-label="templateElement.name + 'ImprovementReviewer'"></div>
      </template>
      <template v-else-if="templateElement.type === 'CompetenceElement'">
        <p data-betsoc-label="competence"></p>
        <progress-radio-input :name="templateElement.name + 'Reviewer'" disabled></progress-radio-input>
        <div :data-betsoc-label="templateElement.name + 'Reviewer'"></div>
      </template>
      <template v-else-if="templateElement.type === InputFields.ASSESSMENT">
        <h2 class="section">{{ templateElement.label }}</h2>
        <assessment-progress
            :measurement-points="measurementPoints[templateElement.skill]"
            v-bind:key="measurementPoints[templateElement.skill]"
            @show-overlay="(e) => this.showFormOverlay(e)"></assessment-progress>
        <progress-radio-input :name="templateElement.name + 'Assessment'" disabled></progress-radio-input>
        <div :data-betsoc-label="templateElement.name + 'Assessment'"></div>
        <progress-radio-input :name="templateElement.name + 'AssessmentReviewer'" disabled></progress-radio-input>
        <div :data-betsoc-label="templateElement.name + 'AssessmentReviewer'"></div>
      </template>
      <template v-if="templateElement.type === InputFields.REVIEW_TEXT">
        <div :data-betsoc-label="templateElement.name"></div>
        <div :data-betsoc-label="templateElement.name + 'Reviewer'"></div>
        <br>
      </template>
      <template v-else-if="templateElement.type === InputFields.RETRO_SKILL">
        <skill-radio label="Feedback gevraagd op vaardigheid" name="feedbackSkill" disabled></skill-radio>
        <skill-radio label="Beste skill" name="feedbackSkillReviewer"></skill-radio>
      </template>
      <template v-else-if="extraSkill && templateElement.type === InputFields.EXTRA_SKILL">
        <div v-if="extraSkill" id="extraSkill">
          <h3>{{extraSkill.label}}</h3>
          <h4>toelichting</h4>
          <progress-radio-input name="extraSkill" disabled></progress-radio-input>
          <div data-betsoc-label="extraSkill"></div>
          <progress-radio-input :name="'extraSkillReviewer'" disabled></progress-radio-input>
          <h4>complimenten</h4>
          <div :data-betsoc-label="'extraSkillComplimentReviewer'"></div>
          <h4>verbeterpunten</h4>
          <div :data-betsoc-label="'extraSkillImprovementReviewer'"></div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import {markdown} from "@/utils/markdown";
import EmotionRadioInput from "@/modules/formelements/components/EmotionRadioInput.vue";
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
import {Form, InputFields, Skill} from "@/utils/constants";
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import OverlayDiv from "@/components/OverlayDiv.vue";
import SkillRadio from "@/modules/formelements/components/SkillRadio.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "DoneForm",
  components: {SkillRadio, OverlayDiv, AssessmentProgress, EmotionRadioInput, ProgressRadioInput},
  props: {
    formPropInfo: Object,
    formId: Number,
  },
  data() {
    return {
      extraSkill: undefined,
      id: undefined,
      meta: {formTemplate: {templateName:'', elements: []}},
      InputFields: InputFields,
      measurementPoints: {},
      overlayData: {},
    }
  },
  computed: {
    formInfo() {
      return (this.formPropInfo) ? this.formPropInfo : this.$route.meta.formType;
    }
  },
  beforeMount() {
    this.id = (this.formId) ? this.formId : this.$route.params.id;
  },
  mounted() {
    this.setupForm(this.id);
    // this.fetchForm();
    // if (this.formInfo === Form.Assessment) this.fetchProgress();
  },
  methods: {
    // fetchForm() {
    //   return fetch(`/api/${this.formInfo.apiEndpoint}/${this.id}/done`)
    //       .then(response => response.json())
    //       .then(json => {
    //         this.processExtraInformation(json);
    //         return json;
    //       })
    //       .then(a => {
    //         const names = Object.keys(a)
    //         names.forEach(name => {
    //           const inputField = document.querySelector(`[name="${name}"]`);
    //           if (inputField == null || a[name] == null) return;
    //           switch (inputField.constructor) {
    //             case HTMLInputElement: {
    //               if (inputField.type === "radio") {
    //                 document.querySelector(`input[name="${name}"][value="${a[name]}"]`).checked = true;
    //               }
    //               break;
    //             }
    //             case HTMLDivElement: {
    //               inputField.innerHTML = markdown.render(a[name]);
    //               break;
    //             }
    //             case HTMLParagraphElement:  {
    //               inputField.innerText = a[name];
    //               break;
    //             }
    //           }
    //         });
    //         return a;
    //       })
    // },
    async setupForm(id) {
      const meta = this.fetchMeta(id);
      const data = this.fetchForm(id);
      const dataReviewer = this.fetchFormReviewer(id);

      const result = await Promise.all([meta, data, dataReviewer]);

      this.meta = result[0];

      const jsonData = {...result[1], ...result[2]};



      await this.$nextTick(() => {
        const names = Object.keys(jsonData)
        names.forEach(name => {
          const inputField = document.querySelector(`[data-betsoc-label="${name}"]`);
          if (inputField == null || jsonData[name] == null) return;
          switch (inputField.constructor) {
            case HTMLInputElement: {
              if (inputField.type === "radio") {
                document.querySelector(`input[data-betsoc-label="${name}"][value="${jsonData[name]}"]`).checked = true;
              }
              break;
            }
            case HTMLDivElement: {
              inputField.innerHTML = markdown.render(jsonData[name]);
              break;
            }
            case HTMLParagraphElement:  {
              inputField.innerText = jsonData[name];
              break;
            }
          }
        });
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
            if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    fetchFormReviewer(id) {
      return fetchAuth(`/api/form/${id}/review`)
          .then(response => response.json())
          .then(json => {
            if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    processExtraInformation(json) {
      this.extraSkill = {label:Skill[json["extraSkill"]], name:"extraSkill"};
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
    downloadAsPDF() {
      import("jspdf").then(jsPDF => {
        const content = document.body.cloneNode(true)
        const pdf = new jsPDF.jsPDF('portrait', 'pt', 'a4');

        const app = content.children[1];
        app.removeChild(app.children[0])
        const wrapper = app.children[0]
        wrapper.removeChild(wrapper.children[0])

        pdf.html(content.firstChild.parentElement, {
          callback: (doc) => {
            doc.save(`${this.meta.formTemplate.templateName}.pdf`);
          },
          windowWidth: window.innerWidth,
          width: 596,

        })
      })

    }
  }
}
</script>

<style src="@/assets/css/form.less" />

<style scoped lang="less">
.done-form-wrapper {
  width: 100vw;
  h1 {
    margin-top: 0;
    padding-top: 1.34rem;
  }
}
</style>l