<template>
  <div style="width: 100vw">
    <overlay-div ref="overlay">
      <DoneForm :form-prop-info="this.overlayData.formType" :form-id="this.overlayData.id"></DoneForm>
    </overlay-div>
    <form @change="testFormChange" @submit.prevent="submitFromRefactor">
      <h1 class="form-title" contenteditable aria-label="form title" @input="changeName" @keydown="preventEnter">{{ this.meta.name }}</h1>
      <div v-for="templateElement in this.layout" v-bind:key="templateElement" class="form-section">
          <Component
              :is="templateElement.type"
              :label="templateElement.label"
              :name="templateElement.name"
              :validator="templateElement.validator"
          />
      </div>
      <div class="form-buttons">
        <button type="button" @click="this.updateForm(false, true)">tussentijds opslaan</button>
        <button type="submit">submit</button>
      </div>
    </form>
  </div>

</template>

<script>
import {markdown} from "@/utils/markdown";
import fetchAuth from "@/utils/authrequest";
import {fillFormElements} from "@/modules/formelements/formElementsIndex";

export default {
  name: "DynamicFillForm",
  components: fillFormElements,
  data() {
    return {
      meta: {formTemplate: {templateName:'', elements: [], reviewPolicy: undefined}},
      layout: [],
      previousChange: Date.now(),
      isCtrlPressed: false,
      // extraSkill: undefined,
      // momento: new Map(),
      // hboi: [],
      // measurementPoints: {},
      // formEnum: Form,
      overlayData: {},
      // dataTypePickableOptions: [],
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
  methods: {
    async setupForm(id) {
      const meta = this.fetchMeta(id);
      const data = this.fetchForm(id);


      const metaResult = await Promise.all([meta]);
      const layout = this.fetchLayout(metaResult[0].formTemplate.templateName, metaResult[0].formTemplate.version);

      const result = await Promise.all([data, meta, layout]);

      result[2].filter(e => e.label.includes('`'))
          .forEach(e => e.label = e.label.split("`")[0]);

      this.layout = result[2];
      this.meta = result[1];

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
          // .then(json => {
          //   // if (this.$route.meta.formType === Form.KnowledgeSharing) this.processExtraInformation(json);
          //   return json;
          // })
    },
    fetchLayout(templateName, version) {
      return fetchAuth(`/api/formtemplate/${templateName}/${version}`, {cache: "force-cache"})
          .then(response => response.json())
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
          if (this.meta.reviewPolicy !== "AUTOMATIC_TO_GROUP" && this.meta.reviewPolicy !== null) {
            this.$router.push({ path: `/form/reviewer/${this.$route.params.id}/${this.meta.reviewPolicy}`})
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
    showFormOverlay(e) {
      this.overlayData = {formType:this.meta.formTemplate.templateName, id:e.id}
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
      this.updateForm(true, false);
    },
    changeName(e) {
      this.previousChange = Date.now();
      setTimeout(() => {
        if (Date.now() - this.previousChange >= 2000) {
          fetchAuth(`/api/form/${this.$route.params.id}/name/${e.target.textContent.replace(/[\r\n]/gm, '')}`, {method: 'PATCH'})
          this.previousChange = Date.now();
        }
      }, 2000)
    },
    preventEnter(e) {
      const keycode = e.charCode || e.keyCode;
      if (keycode  === 13) { //Enter key's keycode
        e.preventDefault();
        return false;
      }
    }
  },
}
</script>

