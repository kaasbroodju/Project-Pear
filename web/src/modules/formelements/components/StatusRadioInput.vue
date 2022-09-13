<template>
  <label
      v-for="key in this.statuses"
      v-bind:key="key"
  >
    <input
        type="radio"
        :name="name + 'Status'"
        :data-betsoc-label="name + 'Status'"
        :value="key.value"
        @change="this.$emit('change', key)"
    >{{ key.name }}</label>
</template>

<script>
export default {
  name: "StatusRadioInput",
  props: {
    name: String
  },
  data() {
    return {
      statuses: [{name:'gepland', value:'PLANNED'}, {name:'in ontwikkeling', value:'IN_DEVELOPMENT'}, {name:'behaald', value:'ACHIEVED'}]
    }
  }
}
</script>

<style scoped>

</style>