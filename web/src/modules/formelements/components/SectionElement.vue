<template>
    <h2 class="section">{{ label }}</h2>
</template>
<script>
export default {
  name: 'section-element',
  props: {
    label: "",
    name: ""
  }
}
</script>