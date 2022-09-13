<template>
  <div>
    <div :data-betsoc-label="name" />
    <text-box :label="label" :name="name + 'Reviewer'" />
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