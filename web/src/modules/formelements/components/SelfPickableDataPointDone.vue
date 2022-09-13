<template>
  <div>
    <p class="label" :data-pear-required="extraSkill != null">{{ label }}</p>
    <input type="hidden" v-if="extraSkill == null" :data-betsoc-label="name + 'DataPointType'" :onresize="() => this.changeExtraSkill()" ref="hiddenInput">
    <criteria-done v-if="extraSkill != null" :label="extraSkill" :name="name" :validator="extraSkill !== null ? {} : validator"></criteria-done>
  </div>
  </template>

<script>
import DataPointTypeRadioInput from "./DataPointTypeRadioInput.vue";
import criteriaStudent from "./CriteriaStudent.vue";
import fetchAuth from "@/utils/authrequest";
import CriteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import CriteriaDone from "@/modules/formelements/components/CriteriaDone.vue";

export default {
  name: "SelfPickableDataPoint",
  components: {CriteriaDone, CriteriaReviewer, DataPointTypeRadioInput, criteriaStudent},
  props: {
    label: "",
    name: String,
    validator: Object,
  },
  data() {
    return {
      extraSkill: null,
      options: [],
    }
  },
  methods: {
    changeExtraSkill() {
      this.extraSkill = this.$refs.hiddenInput.value;
    },
  },

}
</script>

<style scoped>

</style>