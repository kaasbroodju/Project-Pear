<template>
  <div class="old-links-wrapper">
    <div class="old-links-grid">
      <goto-form-done
          v-for="old in newOld" v-bind:key="old.id"
          :id="old.id"
          suffix="done"
          :formTemplate="old.type"
          :formName="old.name"
      />
    </div>
  </div>

</template>

<script>
import GotoFormDone from "@/components/GotoFormDone.vue";
import fetchAuth from "@/utils/authrequest";
import {db} from "@/db/db";
import {liveQuery} from "dexie";
import {useObservable} from "@vueuse/rxjs/index";

export default {
  name: "Old",
  components: { GotoFormDone },
  data() {
    return {
      olds: [],
      newOld: useObservable(liveQuery(() => db.oldForms.orderBy('finishedAt').reverse().toArray()))
    }
  },
  beforeMount() {
    fetchAuth("/api/old")
        .then(response => response.json())
        .then(json => {
          db.oldForms.bulkPut(json);
          // this.olds = json;
        })
  },
}
</script>

<style scoped lang="less">

.old-links-wrapper {
  width: calc(100vw);
}

.old-links-grid {

  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;



  padding: 10px;

  a {
    margin: 10px;
  }
}

</style>