<template>
  <div></div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "CreateFrom",
  mounted() {
    fetchAuth(`/api/form/create/${this.$route.params.template}`, {method: "post"})
        .then(response => response.text())
    .then((id) => {
      this.$router.push({path: `/form/${id}/fill`})
    })
  }
}
</script>

<style scoped>
div {
  min-height: calc(100vh - 5.375em);
}
</style>