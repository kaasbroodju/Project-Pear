<template>
  <div>
    <p class="label">{{label}}</p>
    <div :data-betsoc-label="name"></div>
  </div>

</template>

<script>
export default {
  name: "TextBoxDone",
  props: {
    name: "",
    label: "",
    validator: Object,
  }
}
</script>

<style scoped>

</style>