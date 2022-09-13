<template>
  <div>
    <h2>{{label}}</h2>
    <progress-radio-input :name="name" :disabled="disabled" :notApplicable="notApplicable" @update-form="(e) => this.$emit('updateForm', e)" :validator="validator"></progress-radio-input>
    <text-box label="toelichting/bewijsvorming" :name="name" :disabled="disabled" @update-form="(e) => this.$emit('updateForm', e)" :validator="validator"></text-box>
  </div>
  </template>

<script>
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
import TextBox from "@/modules/formelements/components/TextBox.vue";

export default {
  name: "CriteriaStudent",
  components: {ProgressRadioInput, TextBox},
  props: {
    label: String,
    name: String,
    disabled: Boolean,
    notApplicable: Boolean,
    validator: Object,
  },
  emits: ['updateForm']
}
</script>

<style scoped>
:deep(.text-box-wrapper) {
  margin-top: 1.5em;
}
</style>