<template>
  <div>
    <p class="label" :data-betsoc-label="name"></p>
    <p :data-betsoc-label="name + 'Description'"></p>
    <progress-radio-input :name="name + 'Reviewer'" disabled></progress-radio-input>
    <div :data-betsoc-label="name + 'Reviewer'"></div>
  </div>
</template>

<script>
import ProgressRadioInput from "@/components/ProgressRadioInput.vue";
export default {
  name: "CompetenceDone",
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