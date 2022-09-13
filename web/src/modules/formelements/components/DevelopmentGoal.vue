<template>
  <div>
    <data-point-type-select :dataPointTypes="this.pickableOptions" label="vaardigheid" :name="name + 'DataPointType'" />
    <br>
    <br>
    <text-box label="beschrijving" :name="name + 'Description'" />
    <p class="label">huidig niveau</p>
    <progress-radio-input :name="name + 'Current'"></progress-radio-input>
    <p class="label">doel niveau</p>
    <progress-radio-input :name="name + 'Target'"></progress-radio-input>
    <p class="label">status</p>
    <status-radio-input :name="name"></status-radio-input>
  </div>
</template>

<script>
import DataPointTypeSelect from "@/modules/formelements/components/DataPointTypeSelect.vue";
import textBox from "@/modules/formelements/components/TextBox.vue";
import progressRadioInput from "@/components/ProgressRadioInput.vue";
import StatusRadioInput from "@/modules/formelements/components/StatusRadioInput.vue";

export default {
  name: "DevelopmentGoal",
  props: {
    pickableOptions: Array,
    name: String
  },
  components: { DataPointTypeSelect, textBox, progressRadioInput, StatusRadioInput }
}
</script>

<style scoped>

</style>