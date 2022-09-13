<template>
  <div>
    <p class="label">{{label}}</p>
    <progress-radio-input :name="name" notApplicable disabled @is-not-applicable="hideProgressElement"></progress-radio-input>
    <div :data-betsoc-label="name"></div>
    <progress-radio-input v-if="!this.hideProgress" :name="name + 'Reviewer'" disabled></progress-radio-input>
    <div :data-betsoc-label="name + 'ComplimentReviewer'"></div>
    <div :data-betsoc-label="name + 'ImprovementReviewer'"></div>
  </div>

</template>

<script>
import criteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import progressRadioInput from "@/components/ProgressRadioInput.vue";


export default {
  name: "NotApplicableDataPointDone",
  components: {progressRadioInput, criteriaReviewer},
  props: {
    label: String,
    name: String,
    disabled: Boolean,
    validator: Object
  },
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