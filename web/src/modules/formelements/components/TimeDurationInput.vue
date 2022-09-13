<template>
  <p class="label" :data-pear-required="validator !== null">{{ label }}</p>
  <input type="time" :name="name" :data-betsoc-label="name" value="00:00" :disabled="disable" :required="validator !== null">
</template>

<script>
export default {
  name: "TimeDurationInput",
  props: {
    name: String,
    label: String,
    disable:  Boolean,
    validator: Object
  }
}
</script>

<style scoped>
  input {
    font-size: 4em;
    background-color: transparent;
    color: var(--text);
    border: none;
  }
</style>