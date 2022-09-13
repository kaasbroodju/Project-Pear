<template>
  <div>
    <p class="label" :data-betsoc-label="name"></p>
    <p :data-betsoc-label="name + 'Description'"></p>
    <criteria-student label="" :name="name + 'Reviewer'"></criteria-student>
  </div>
</template>

<script>
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";
export default {
  name: "CompetenceReviewer",
  components: {CriteriaStudent},
  props: {
    name: "",
    label: "",
    validator: Object,
  }
}
</script>

<style scoped>

</style>