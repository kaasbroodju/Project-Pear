<template>
  <div>
    <label v-if="notApplicable">
      <input
          type="radio"
          :name="'progress' + name"
          :data-betsoc-label="'progress' +  name"
          value="null"
          :disabled="disabled"
          @change="this.$emit('updateForm')"
          :onresize="this.raiseNotApplicable"
      >
      Not applicable
    </label>
    <label v-for="key in Object.keys(progress)" v-bind:key="key">
      <input
          :required="validator !== null"
          type="radio"
          :name="'progress' + name"
          :data-betsoc-label="'progress' + name"
          :value="key"
          :disabled="disabled"
          @change="this.$emit('updateForm')"
      >
      {{progress[key]}}
    </label>
  </div>
</template>

<script>
import {Progress} from "@/utils/constants";

export default {
  name: "ProgressRadioInput",
  props: {
    name: String,
    disabled: Boolean,
    notApplicable: Boolean,
    validator: Object,
  },
  data() {
    return {
      progress: Progress,

    }
  },
  methods: {
    raiseNotApplicable() {
      this.$emit('isNotApplicable');
    }
  },
  emits: ['updateForm', 'isNotApplicable']
}
</script>

<style scoped>

</style>