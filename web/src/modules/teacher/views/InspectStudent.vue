<template>
  <div style="width: 100vw">
    <h1>{{ studentName }}</h1>
    <h2>gevoelens</h2>
    <emotions-diagram :student-email="this.$route.params.email" />
    <h2>tijd besteding</h2>
    <time-duration-graph :student-email="this.$route.params.email" />
    <h2>vaardigheden</h2>
    <button type="button" @click="this.showMeasurement = !this.showMeasurement">{{ this.showMeasurement ? 'hide vaardigheden' : 'show vaardigheden' }}</button>
    <div v-show="showMeasurement">
      <div v-for="(value, key) in measurementPoints" v-bind:key="key" class="skill">
        <h3>{{ key }}</h3>
        <assessment-progress :measurement-points="value"></assessment-progress>
      </div>
    </div>

    <table>
      <caption>reviews in afwachting</caption>
      <thead hide>

      <tr>
        <td>type</td>
        <td>naam</td>
        <td>competentie</td>
        <td>persoon</td>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="review in awaitingReviews"
          v-bind:key="review"
      >
        <td class="template-image-container" style="width: 5%"><img :src="`/img/${review.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="review.templateName" :title="review.templateName"></td>
        <td>{{ review.name }}</td>
        <td v-if="review.mainCompetence !== null">{{ `${review.mainCompetence} ${review.subCompetence} ${review.level}` }}</td>
        <td v-else></td>
        <td>{{ review.studentName }}</td>
      </tr>
      <tr v-if="awaitingReviews.length === 0">
        <td colspan="4" style="text-align: center; font-style: italic" class="mid-section">leerling heeft geen reviews in afwachting.</td>
      </tr>
      </tbody>
    </table>
    <h2>form history</h2>
    <table id="old-review-table">
      <caption>form history</caption>
      <thead hide>

      <tr>
        <td>type</td>
        <td>naam</td>
      </tr>
      </thead>
      <tbody>
      <router-link
          v-for="(review, index) in completedForms"
          v-bind:key="review"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/form/${review.id}/done`"
      >
        <tr
            tabindex="0"
            :href="`/form/${review.id}/done`"
            @click="navigate"
            :class="index === completedForms.length-2 ? 'observable' : ''"
            :id="index"
        >
          <td class="template-image-container" style="width: 5%"><img :src="`/img/${review.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="review.templateName" :title="review.templateName"></td>
          <td>{{ review.name }}</td>
        </tr>
      </router-link>
      </tbody>
    </table>
  </div>

</template>

<script>
import fetchAuth from "@/utils/authrequest";
import AssessmentProgress from "@/components/AssessmentProgress.vue";
import EmotionsDiagram from "@/components/progresscomponents/EmotionsDiagram.vue";
import TimeDurationGraph from "@/components/progresscomponents/TimeDurationGraph.vue";

export default {
  name: "InspectStudent",
  components: {TimeDurationGraph, EmotionsDiagram, AssessmentProgress},
  data() {
    return {
      awaitingReviews: [],
      measurementPoints: {},
      showMeasurement: false,
      studentName: "",
      completedForms: []
    }
  },
  mounted() {
    fetchAuth(`/api/teacher/review/awaiting/${this.$route.params.email}`)
        .then(response => response.json())
        .then(json =>  {
          this.awaitingReviews = json;
        })
    fetchAuth("/api/datapointtype")
        .then(response => response.json())
        .then(result => {
          result.forEach((dataPointType) => {
            fetchAuth(`/api/teacher/student/${this.$route.params.email}/progress/${dataPointType}`)
                .then(response => response.json())
                .then(json => {
                  this.measurementPoints[dataPointType] = json
                })
          })
        })
    fetchAuth(`/api/teacher/student/${this.$route.params.email}`).then(response => response.text()).then(name => this.studentName = name);
    fetchAuth(`/api/teacher/student/form/${this.$route.params.email}/old`)
        .then(response => response.json())
        .then(json =>  {
          this.completedForms = json;
        })
  }
}
</script>

<style src="@/assets/css/table.less" />

<style scoped lang="less">

h1 {
  text-align: center;
}

.skill {
  background-color: var(--background-soft);
  margin-bottom: 4em;

  h3 {
    margin: 0 0 0 1rem;
    padding-bottom: 1em;
    padding-top: 1em;
  }
}
</style>