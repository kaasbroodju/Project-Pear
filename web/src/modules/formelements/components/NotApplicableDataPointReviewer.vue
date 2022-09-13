<template>
  <div>
    <criteria-reviewer :name="name" :label="label" not-applicable></criteria-reviewer>
  </div>

</template>

<script>
import criteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import progressRadioInput from "@/components/ProgressRadioInput.vue";


export default {
  name: "NotApplicableDataPointReviewer",
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