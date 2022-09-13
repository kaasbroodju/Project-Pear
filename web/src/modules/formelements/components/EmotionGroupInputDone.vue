<template>
  <div>
    <p class="label">
      {{ label }}
    </p>
    <div style="display: inline-flex">
      <emotion-radio-input emotion="ZEER_SLECHT" :required="validator !== null" disable></emotion-radio-input>
      <emotion-radio-input emotion="SLECHT" :required="validator !== null" disable></emotion-radio-input>
      <emotion-radio-input emotion="NEUTRAAL" :required="validator !== null" disable></emotion-radio-input>
      <emotion-radio-input emotion="GOED" :required="validator !== null" disable></emotion-radio-input>
      <emotion-radio-input emotion="ZEER_GOED" :required="validator !== null" disable></emotion-radio-input>
    </div>
  </div>

</template>

<script>
import EmotionRadioInput from "@/modules/formelements/components/EmotionRadioInput.vue"

export default {
  name: 'emotion-group-input-done',
  components: {EmotionRadioInput},
  props: {
    label: "",
    name: "",
    validator: Object,
  }
}
</script>
