<template>
  <div style="width: 100vw">
    <overlay-div ref="overlay">
      <DoneForm :form-prop-info="this.overlayData.formType" :form-id="this.overlayData.id"></DoneForm>
    </overlay-div>
    <button id="pdfButton" type="button" @click="downloadAsPDF">download as pdf</button>
    <form>
      <h1 class="form-title" aria-label="form title">{{ this.meta.name }}</h1>
      <div v-for="templateElement in this.layout" v-bind:key="templateElement" class="form-section">
          <Component
              :is="templateElement.type"
              :label="templateElement.label"
              :name="templateElement.name"
              :validator="templateElement.validator"
          />
      </div>
    </form>
  </div>
</template>

<script>
import {markdown} from "@/utils/markdown";
import fetchAuth from "@/utils/authrequest";
import {doneElements} from "@/modules/formelements/formElementsIndex";

export default {
  name: "DynamicFillForm",
  components: doneElements,
  props: {
    formId: Number,
  },
  data() {
    return {
      meta: {formTemplate: {templateName:''}},
      layout: [],
      extraSkill: undefined,
      overlayData: {},
    }
  },
  mounted() {
    this.setupForm((this.formId) ? this.formId : this.$route.params.id);
  },
  methods: {
    async setupForm(id) {
      const meta = this.fetchMeta(id);
      const data = this.fetchForm(id);
      const reviewerData = this.fetchFormReviewer(id);


      const metaResult = await Promise.all([meta]);
      const layout = this.fetchLayout(metaResult[0].formTemplate.templateName, metaResult[0].formTemplate.version);

      const result = await Promise.all([data, reviewerData, meta, layout]);

      // result[3].filter(e => e.label.includes('`'))
      //     .forEach(e => e.label = e.label.split("`")[1]);

      this.layout = result[3];
      this.meta = result[2];

      await this.$nextTick(() => {});

      const jsonData = {...result[0], ...result[1]}

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
            } else if (inputField instanceof HTMLParagraphElement) {
              inputField.innerText = jsonData[name]
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
      return fetchAuth(`/api/form/${id}`).then(response => response.json())
    },
    fetchFormReviewer(id) {
      return fetchAuth(`/api/form/${id}/review`)
          .then(response => {
            if (response.status === 401) return {};
            return response.json()
          })
          .then(json => {
            // if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
            return json;
          })
    },
    fetchLayout(templateName, version) {
      return fetchAuth(`/api/formtemplate/${templateName}/${version}`, {cache: "force-cache"})
          .then(response => response.json())
    },
    showFormOverlay(e) {
      this.overlayData = {formType:this.meta.formTemplate.templateName, id:e.id}
      this.$refs.overlay.showOverlay();
    },
    downloadAsPDF() {
      import("jspdf").then(jsPDF => {
        const emotionInputs = document.querySelectorAll("input[name='emotion']:checked");

        for (let emotionInput of emotionInputs) {
          const element = document.createElement("div");
          element.innerText = this.translateValue(emotionInput.value) + " out of 5";

          emotionInput.parentElement.parentElement.replaceWith(element);
        }



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

    },
    translateValue(value) {
      switch (value) {
        case "ZEER_GOED":
          return 5;
        case "GOED":
          return 4;
        case "NEUTRAAL":
          return 3;
        case "SLECHT":
          return 2;
        case "ZEER_SLECHT":
          return 1;
        default:
          return -1
      }
    }
  },

}
</script>

<style scoped lang="less">
#pdfButton {
  position: absolute;
}
</style>
