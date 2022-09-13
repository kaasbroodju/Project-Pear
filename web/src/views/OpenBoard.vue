<template>
  <table v-if="openReviews.length > 0">
    <thead hide>
      <tr>
        <th style="width: 5%">soort formulier</th>
        <th style="width: 40%">titel</th>

        <th style="width: 10%;">gevraagde soort reviewer</th>
        <th style="width: 30%">competentie</th>
        <th style="width: 15%; text-align: center">naam</th>
<!--        <th>button group</th>-->
      </tr>
    </thead>
    <tbody>
      <tr v-for="openReview in openReviews" v-bind:key="openReview" tabindex="0" role="button" @click="this.claim(openReview.id)">
        <td class="template-image-container" style="width: 5%"><img :src="`/img/${openReview.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="openReview.templateName" :title="openReview.templateName"></td>
        <td class="template-image-container" style="width: 5%"><img :src="`/img/${openReview.preferredReviewer.toLowerCase()}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="openReview.preferredReviewer.toLowerCase().replaceAll('_', ' ')" :title="openReview.preferredReviewer.toLowerCase().replaceAll('_', ' ')"></td>

        <td style="font-weight: bold; width: 35%">{{ openReview.title }}</td>
        <td v-if="openReview.competence !== null" style="width: 25%">{{ `${openReview.competence.mainCompetence} ${openReview.competence.subCompetence} ${openReview.competence.level}` }}</td>
        <td v-else style="width: 30%"></td>

        <td style="width: 15%; text-align: center">{{ openReview.name }}</td>
<!--        <td><button type="button" @click="this.claim(openReview.id)">Claim</button></td>-->
      </tr>
    </tbody>
  </table>
  <div v-else style="width: 100vw; text-align: center">
    <h1>No available reviews</h1>
  </div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "OpenBoard",
  data() {
    return {
      openReviews: []
    }
  },
  beforeCreate() {
    fetchAuth('/api/openboard').then(response => response.json()).then(json => this.openReviews = json);
  },
  mounted() {

  },
  methods: {
    claim(id) {
      fetchAuth(`/api/openboard/${id}/claim`, {method: "POST"})
          .then(() => {
            this.$router.push({path: `/form/${id}/review`})
          })
    }
  }
}
</script>

<style src="@/assets/css/table.less" />

<style scoped lang="less">
.template-image-container {
  padding-bottom: 0;
  padding-top: 0;

  > img {
    padding-top: 0.5rem;
    height: calc(3em - 0.5rem)
  }
}
</style>

<!--<style scoped lang="less">-->
<!--table {-->
<!--  //margin-top: 1em;-->
<!--  width: 100%;-->
<!--  border-collapse: collapse;-->
<!--  border-spacing: 0;-->

<!--  font-size: 1.25em;-->

<!--  //tbody {-->
<!--  //  tr {-->
<!--  //    td {-->
<!--  //      border: none;-->
<!--  //    }-->
<!--  //-->
<!--  //  }-->
<!--  //-->
<!--  //}-->
<!--}-->
<!--.board-header {-->
<!--  position:absolute;-->
<!--  left:-10000px;-->
<!--  top:auto;-->
<!--  width:1px;-->
<!--  height:1px;-->
<!--  overflow:hidden;-->
<!--}-->
<!--.board-row {-->
<!--  //border-bottom: 1px solid var(&#45;&#45;text);-->
<!--  border-spacing: 1px;-->
<!--  &:not(:last-child) {-->
<!--    border-bottom: 1px solid var(&#45;&#45;text-soft);-->
<!--  }-->


<!--  &:hover, &:focus {-->
<!--    background-color: var(&#45;&#45;background-soft);-->
<!--  }-->
<!--  > td {-->
<!--    padding: 1em 0;-->
<!--  }-->
<!--}-->
<!--</style>-->