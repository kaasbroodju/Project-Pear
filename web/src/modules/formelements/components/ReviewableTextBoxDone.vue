<template>
  <div>
    <p class="label">{{ label }}</p>
    <div :data-betsoc-label="name" />
    <div :data-betsoc-label="name + 'Reviewer'" />
  </div>

</template>

<script>

import TextBox from "@/modules/formelements/components/TextBox.vue";

export default {
  name: "ReviewableTextBoxReview",
  components: {TextBox},
  props: {
    label: String,
    name: String,
    validator: Object,
  },
}
</script>