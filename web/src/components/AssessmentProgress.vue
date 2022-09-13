<template>
  <div class="measurements-wrapper">
    <div class="measurement-graph-wrapper">
      <skill-diagram :measurement-points="sortedMeasurementPoints" @show-overlay="(e) => $emit('showOverlay', e)"></skill-diagram>
    </div>
<!--    <div class="measurement-form-wrapper">-->
<!--      <button-->
<!--          v-for="measurement in measurementPoints"-->
<!--          v-bind:key="measurement"-->
<!--          type="button"-->
<!--          class="show-overlay-button"-->
<!--          @click="$emit('showOverlay', measurement)"-->
<!--      >{{ `${measurement.name} ${new Date(measurement.time).toLocaleDateString('default', { day: 'numeric', month:'short', year:'2-digit'})}` }}</button>-->
<!--&lt;!&ndash;      <router-link&ndash;&gt;-->
<!--&lt;!&ndash;          &ndash;&gt;-->
<!--&lt;!&ndash;          :to="`/form/${measurement.formType}/${measurement.id}/done`"&ndash;&gt;-->
<!--&lt;!&ndash;          target="_blank"&ndash;&gt;-->
<!--&lt;!&ndash;      >{{ `${measurement.id} ${measurement.formType} ${measurement.time}` }}</router-link>&ndash;&gt;-->
<!--    </div>-->

  </div>
</template>

<script>
import SkillDiagram from "@/components/progresscomponents/SkillDiagram.vue";

export default {
  name: "AssessmentProgress",
  components: {SkillDiagram},
  props: {
    measurementPoints: Array,
  },
  beforeMount() {
  },
  computed: {
    sortedMeasurementPoints() {
      if (!this.measurementPoints) return [];
      if (this.measurementPoints.length <= 1) return this.measurementPoints;

      return [...this.measurementPoints].sort((a, b) =>  new Date(a.time) - new Date(b.time))
    }
  }
}
</script>

<style scoped lang="less">
.measurements-wrapper {
  display: flex;

  .measurement-graph-wrapper {
    width: 100%;
  }

  .measurement-form-wrapper {
    display: grid;
    width: 50%;
    margin: 0 10px;
    height: 216px; // todo not optimal for different screen ratio's
    overflow-y: scroll;
    .show-overlay-button {
      font-size: 2em;
      margin-bottom: 0.25em;
      font-family: unset;
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}

</style>