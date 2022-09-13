<template>
  <div class="progress-view-wrapper">
    <h1>gevoelens (per 5 dagen)</h1>
    <emotions-diagram />
    <h1>tijd besteding</h1>
    <TimeDurationGraph />
    <h1>vaardigheden</h1>
    <div v-for="key in dataTypes" v-bind:key="key" class="skill">
      <h2>{{ key }}</h2>
      <assessment-progress :measurement-points="measurementPoints.get(key)"></assessment-progress>
    </div>
  </div>

</template>

<script>
import EmotionsDiagram from "@/components/progresscomponents/EmotionsDiagram.vue";
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import TimeDurationGraph from "@/components/progresscomponents/TimeDurationGraph.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "Progress",
  components: {TimeDurationGraph, AssessmentProgress, EmotionsDiagram},
  data() {
    return {
      dataTypes: [],
      measurementPoints: new Map,
    }
  },
  beforeMount() {

    fetchAuth("/api/datapointtype").then(response => response.json()).then((datatypes) => datatypes.forEach(dt => this.fetchDataTypePoint(dt))).catch(() => []);
  },
  methods: {
    fetchDataTypePoint(dataPointType) {
      fetchAuth("/api/progress/" + dataPointType)
          .then(response => response.json())
          .then(json => {
            this.measurementPoints.set(dataPointType, json);
            this.dataTypes.push(dataPointType)
            // for (let jsonKey in json) {
            //   this.measurementPoints.set(jsonKey, json[jsonKey]);
            // }
            //
          })
    }
  }
}
</script>

<style scoped lang="less">
.progress-view-wrapper {
  width: 100vw;
}

.skill {
  background-color: var(--background-soft);
  margin-bottom: 4em;

  h2 {
    margin: 0 0 0 1rem;
    padding-bottom: 1em;
    padding-top: 1em;
  }
}

h1, h2, h3 {
  margin-left: 1rem;
}
</style>