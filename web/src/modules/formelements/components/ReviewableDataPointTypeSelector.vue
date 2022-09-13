<template>
  <div>
    <p class="label">{{ label }}</p>
    <data-point-type-radio-input :name="name" :validator="validator"></data-point-type-radio-input>
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