<template>
  <div class="home">
    <div class="home-sidebar">
      <user-details></user-details>
    </div>
    <div class="home-right">
      <div class="home-create-form">
        <template
            v-for="form of this.forms"
            v-bind:key="form"
        >
          <create-form-button :form-instance="form"></create-form-button>
        </template>
      </div>

<!--      <div>-->
<!--        <ul>-->
<!--          <li>(feature) tab/shift + tab in forms you fill in stepwise</li>-->
<!--          <li>(feature) icon/foto voor elke skill</li>-->
<!--          <li>(feature) icon/foto voor elke soort type van review</li>-->
<!--          <li>(feature) waarnemings document</li>-->
<!--          <li>(bug fix) self-pickable-data-point meerde op 1 form kan zelfde data point selecteren</li>-->
<!--          <li>(feature) leerlingen kunnen een specialisatie doorgeven/creeren, b.v Java, en mensen kunnen daarop e.v.t opzoeken</li>-->
<!--        </ul>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
import CreateFormButton from "@/components/CreateFormButton.vue";
import UserDetails from "@/components/UserDetails.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: 'StudentHome',
  components: {UserDetails, CreateFormButton},
  data() {
    return {
      forms: []
    }
  },
  beforeMount() {
    fetchAuth("/api/formtemplate")
        .then(response => response.json())
        .then(json => {
          this.forms = json;
        })
  }
}
</script>

<style scoped lang="less">
.home {
  display: grid;
  grid-template-columns: 30vw 70vw;
  min-height: calc(100vh - 5.375em);
  grid-auto-rows: 1fr;

  .home-sidebar {
    background-color: var(--background-sidebar);
  }

  .home-create-form {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;

    padding: 10px;

    > button {
      margin: 10px;
    }
  }
}


</style>
