<template>
  <div>
    <p class="label">{{ normalLabel }}</p>
    <p :data-betsoc-label="name + 'DataPointType'">/</p>
    <p class="label">{{ reviewLabel }}</p>
    <p :data-betsoc-label="'reviewer' + name + 'DataPointType'">/</p>
  </div>
  </template>

<script>
import DataPointTypeRadioInput from "./DataPointTypeRadioInput.vue";
import criteriaStudent from "./CriteriaStudent.vue";
import fetchAuth from "@/utils/authrequest";
import CriteriaReviewer from "@/modules/formelements/components/CriteriaReviewer.vue";
import CriteriaDone from "@/modules/formelements/components/CriteriaDone.vue";

export default {
  name: "ReviewableDataPointTypeSelectorReview",
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
  computed: {
    normalLabel() {
      return this.label.split("`")[0]
    },
    reviewLabel() {
      return this.label.split("`")[1]
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