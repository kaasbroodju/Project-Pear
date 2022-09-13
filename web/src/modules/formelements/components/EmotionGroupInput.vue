<template>
    <p class="label" :data-pear-required="validator !== null">
      {{ label }}
    </p>
    <div style="display: inline-flex">
      <emotion-radio-input emotion="ZEER_SLECHT" :required="validator !== null"></emotion-radio-input>
      <emotion-radio-input emotion="SLECHT" :required="validator !== null"></emotion-radio-input>
      <emotion-radio-input emotion="NEUTRAAL" :required="validator !== null"></emotion-radio-input>
      <emotion-radio-input emotion="GOED" :required="validator !== null"></emotion-radio-input>
      <emotion-radio-input emotion="ZEER_GOED" :required="validator !== null"></emotion-radio-input>
    </div>
</template>

<script>
import EmotionRadioInput from "@/modules/formelements/components/EmotionRadioInput.vue"

export default {
  name: 'emotion-group-input',
  components: {EmotionRadioInput},
  props: {
    label: "",
    name: "",
    validator: Object,
  }
}
</script>
