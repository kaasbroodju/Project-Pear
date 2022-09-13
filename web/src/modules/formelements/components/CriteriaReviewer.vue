<template>
  <div>
    <p class="label">{{label}}</p>
    <progress-radio-input :notApplicable="notApplicable" :name="name" disabled @is-not-applicable="hideProgressElement"></progress-radio-input>
    <div :data-betsoc-label="name"></div>

    <progress-radio-input v-if="!this.hideProgress" :name="name + 'Reviewer'" @update-form="(e) => this.$emit('updateForm', e)"></progress-radio-input>
    <br v-if="!this.hideProgress" role="presentation">
    <text-box label="compliment" :name="name + 'ComplimentReviewer'" @update-form="(e) => this.$emit('updateForm', e)"></text-box>
    <br>
    <text-box label="verbeterpunten" :name="name + 'ImprovementReviewer'" @update-form="(e) => this.$emit('updateForm', e)"></text-box>
  </div>
  </template>

<script>
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
import TextBox from "@/modules/formelements/components/TextBox.vue";

export default {
  name: "CriteriaReviewer",
  components: {ProgressRadioInput, TextBox},
  props: {
    label: String,
    name: String,
    notApplicable: Boolean,
    validator: Object,
  },
  emits: ['updateForm'],
  data() {
    return {
      hideProgress: false,
    }
  },
  methods: {
    hideProgressElement() {
      this.hideProgress = true;
    }
  }
}
</script>

<style scoped>

</style>