<template>
  <label>
    {{ label }}
    <select :name="name" :data-betsoc-label="name">
      <option
          v-for="dataPointType in dataPointTypes"
          :value="dataPointType"
      >{{ dataPointType }}</option>
    </select>
  </label>

</template>

<script>
export default {
  name: "DataPointTypeSelect",
  props: {
    name: String,
    label: String,
    dataPointTypes: Array,
  },
}
</script>

<style scoped>

</style>