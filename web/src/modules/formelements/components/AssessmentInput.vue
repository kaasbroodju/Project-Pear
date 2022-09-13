<template>
    <h2 class="section">{{ label }}</h2>
    <assessment-progress
        :measurement-points="measurementPoints[name]"
        v-bind:key="measurementPoints[name]"
        @show-overlay="(e) => this.showFormOverlay(e)"></assessment-progress>
    <criteria-student label="" :name="name + 'Assessment'"></criteria-student>
</template>
<script>
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";

export default {
  name: 'assessment-input',
  components: {AssessmentProgress, CriteriaStudent},

  props: {
    label: '',
    name: '',
    measurementPoints: {},
  }
}
</script>