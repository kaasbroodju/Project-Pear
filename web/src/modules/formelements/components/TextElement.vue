<template>
  <div class="text-box-wrapper">
    <div class="text-area-header">
      <button @click="this.showPreview = false" type="button" :class="`${showPreview ? '' : 'button-selected'}`">edit</button>
      <button @click="this.showPreview = true" type="button" :class="`${showPreview ? 'button-selected': ''}`">preview</button>
      <p class="label required-form-element">{{ label }}</p>
    </div>
<!--    <div :style="`display: ${showPreview ? 'none' : ''};`">-->
      <textarea
          :name="name"
          :data-betsoc-label="name"
          required
          :disabled="disabled"
          :visible="showPreview"
          :aria-label="label"
          ref="textArea"
          v-show="!showPreview"
          @input="() => {this.resize(); this.$emit('updateForm')}"
          @paste="(e) => {this.resize(); this.$emit('updateForm'); this.pasteImageHandler(e)}"
          style="height: 1em"
          :onresize="this.resize"
      />

      <div v-show="showPreview" class="preview" ref="preview">
      </div>
<!--    </div>-->

  </div>

</template>

<script>
import {markdown} from "@/utils/markdown";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "TextElement",
  props: {
    label: String,
    name: String,
    required: Boolean,
    disabled: Boolean,
  },
  data() {
    return {
      test: undefined,
      showPreview: false,
    }
  },
  mounted() {
    setTimeout(this.resize, 0)
    //
    // fetch("/test")
  },
  methods: {
    pasteImageHandler(e) {
      if (e.clipboardData.files.length === 0) return;
      e.preventDefault();

      const file = e.clipboardData.files[0];

      const formData = new FormData()
      formData.append('image', file)


      fetchAuth(`/api/form/${this.$route.params.id}/markdown/file`, {method: "POST", body: formData}).then(response => {if (!response.ok) throw "error"; return response.text()})
          .then((id) => {
            this.paste(e.target, file.name, id);
      })
    },
    paste(field, fileName, id) {
      const insertString = `![${fileName}](/api/form/${this.$route.params.id}/markdown/file/${id})`
      if (field.selectionStart || field.selectionStart == '0') {
        const startPos = field.selectionStart;
        const endPos = field.selectionEnd;
        field.value = field.value.substring(0, startPos)
            + insertString
            + field.value.substring(endPos, field.value.length);
      } else {
        field.value += insertString;
      }
    },
    createPreview() {
      this.showPreview = true;

      this.test = this.$refs.textArea.value;
      this.$refs.preview.innerHTML = markdown.render(this.$refs.textArea.value);

      // this.$refs.preview.innerHTML = markdown.render(document.querySelector(`#${this.name}`).value);
      //
      //
      // this.$refs.preview.innerHTML = markdown.toHTML(markdown.parse(document.querySelector(`#${this.name}`).value));

    },
    resize() {

      this.$refs.textArea.style.height = 'auto';
      this.$refs.textArea.style.height = `${this.$refs.textArea.scrollHeight}px`;
    },
    goToEditMode() {
      this.showPreview = false;

      setTimeout(() => this.$refs.textArea.focus(), 0);
    },
  },
  watch: {
    showPreview(value) {
      if (value) {
        this.$refs.preview.innerHTML = markdown.render(this.$refs.textArea.value);
      } else {
        this.$nextTick(() => {
          this.$refs.textArea.focus();
          this.resize();
        });
      }


    }
  },
  emits: ['updateForm']
}
</script>

<style scoped lang="less">

.text-box-wrapper {
  display: grid;
  grid-template-rows: calc(3em + 2px) auto;
}
  textarea {
    background-color: transparent;
    //border-radius: 0;
    overflow: hidden;
    border: black 1px solid;
    color: var(--text);
    border-radius: 0 1rem 1rem 1rem;
    padding: 1rem;
    resize: none;
    margin: 0
  }
  label[visible="false"] {
    display: none;
  }
  .text-area-header {
    display: flex;
    float: left;
    align-content: center;
    width: 100%;

    &> p {
      padding-left: 1em;
      margin: unset;
      align-self: center;
    }
    & > button {
      border: black 1px solid;
      background-color: var(--background);
      border-radius: 0;
      width: 5em;
      border-bottom: none;

      &.button-selected {
        background-color: var(--background-sidebar);
      }

      &:hover, &:focus {
        background-color: var(--background-soft);
      }

      &:first-child {
        border-right: none;
        border-radius: 1rem 0 0 0;
      }
      &:last-of-type {
        border-radius: 0 1rem 0 0;
      }
    }
  }
  .preview {
    //display: flex !important;
    border-radius: 0 1rem 1rem 1rem;
    border: black 1px solid;

    padding: 1em;

    min-height: 4em;
    //width: 100%;

  }

.preview img {
  background-color: white !important;
}

</style>