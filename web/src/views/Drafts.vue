<template>
  <div class="draft-links-wrapper view-wrapper">
    <goto-form-done
        v-for="draft in drafts" v-bind:key="draft.id"
        :id="draft.id"
        suffix="fill"
        :formTemplate="draft.type"
        :form-name="draft.name"
    />
  </div>
</template>

<script>
import GotoFormDone from "@/components/GotoFormDone.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "Drafts",
  components: { GotoFormDone },

  data() {
    return {
      drafts: [],
    }
  },
  beforeMount() {
    fetchAuth("/api/drafts")
        .then(response => response.json())
    .then(json => {

      this.drafts = json;
    })
  }
}
</script>

<style scoped lang="less">
.draft-links-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;

  width: calc(100vw - 20px);

  padding: 10px;

  a {
    margin: 10px;
  }
}

</style>