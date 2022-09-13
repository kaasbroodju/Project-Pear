<template>
  <form @submit.prevent="sendForm">
    <label>
      title
      <input type="text" name="title" minlength="5" maxlength="32" required>
    </label>
    <div>
      <label v-for="key in Object.keys(reviewTypes)" v-bind:key="key">
        <input
            type="radio"
            name="reviewTypeWanted"
            :value="key"
            required
        >
        {{reviewTypes[key]}}
      </label>
    </div>
    <button type="submit">put on open board</button>
  </form>
</template>

<script>
import {ReviewTypes} from "@/utils/constants";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "AddToBoard",
  data() {
    return {
      reviewTypes: ReviewTypes,
    }
  },
  methods: {
    sendForm(e) {

      fetchAuth("/api/openboard/" + this.$route.params.id, {
        method: "post",
        body: this.serializeAndJsonify(e.target),
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      })
        .then(response => response.ok)
        .then(ok => {
          if (ok) {
            this.$router.push({path: "/home"})
          }
        })
    },
    serializeAndJsonify(form) {
      let obj = {};
      let formData = new FormData(form);
      for (const key of formData.keys()) {
        obj[key] = formData.get(key);
      }
      return JSON.stringify(obj);
    }
  }
}
</script>

<style scoped>

</style>