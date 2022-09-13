<template>
  <criteria-student :label="label" :name="name" not-applicable :disabled="disabled" :validator="{}"></criteria-student>
</template>

<script>
import CriteriaStudent from "@/modules/formelements/components/CriteriaStudent.vue";
export default {
  name: "NotApplicableDataPointStudent",
  components: {CriteriaStudent},
  props: {
    label: String,
    name: String,
    disabled: Boolean,
    validator: Object,
  }
}
</script>

<style scoped>

</style>