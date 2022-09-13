<template>
  <div>
    <p class="label">{{label}}</p>

    <progress-radio-input :name="name" disabled></progress-radio-input>

    <div :data-betsoc-label="name"></div>
    <progress-radio-input :name="name + 'Reviewer'" disabled></progress-radio-input>
    <div :data-betsoc-label="name + 'ComplimentReviewer'"></div>
    <div :data-betsoc-label="name + 'ImprovementReviewer'"></div>
  </div>

</template>

<script>
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
export default {
  name: "CriteriaDone",
  components: {ProgressRadioInput},
  props: {
    name: "",
    label: "",
    validator: Object,
  }
}
</script>

<style scoped>

</style>