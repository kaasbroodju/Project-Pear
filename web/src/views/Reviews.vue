<template>
  <div class="review-view-wrapper">
    <table>
      <caption under-nav-bar>open staande reviews</caption>
      <thead hide>

      <tr>
        <td>type</td>
        <td>naam</td>
        <td>competentie</td>
        <td>persoon</td>
      </tr>
      </thead>
      <tbody>
      <router-link
          v-for="review in pendingReviews"
          v-bind:key="review"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/form/${review.id}/review`"
      >
        <tr
            tabindex="0"
            :href="`/form/${review.id}/review`"
            @click="navigate"
        >
          <td class="template-image-container" style="width: 5%"><img :src="`/img/${review.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="review.templateName" :title="review.templateName"></td>
          <td>{{ review.name }}</td>
          <td v-if="review.mainCompetence !== null">{{ `${review.mainCompetence} ${review.subCompetence} ${review.level}` }}</td>
          <td v-else></td>
          <td>{{ review.studentName }}</td>
        </tr>

      </router-link>
      <tr v-if="pendingReviews.length === 0">
        <td colspan="4" style="text-align: center; font-style: italic" class="mid-section">geen open staande reviews (づ｡◕‿‿◕｡)づ</td>
      </tr>
      </tbody>
    </table>
    <table v-if="view !== 'ROLE_TEACHER'">
      <caption>reviews in afwachting</caption>
      <thead hide>

      <tr>
        <td>type</td>
        <td>naam</td>
        <td>competentie</td>
        <td>persoon</td>
      </tr>
      </thead>
      <tbody>
      <router-link
          v-for="review in awaitingReviews"
          v-bind:key="review"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/form/${review.id}/done`"
      >
        <tr
            tabindex="0"
            :href="`/form/${review.id}/done`"
            @click="navigate"
        >
          <td class="template-image-container" style="width: 5%"><img :src="`/img/${review.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="review.templateName" :title="review.templateName"></td>
          <td>{{ review.name }}</td>
          <td v-if="review.mainCompetence !== null">{{ `${review.mainCompetence} ${review.subCompetence} ${review.level}` }}</td>
          <td v-else></td>
          <td>{{ review.studentName }}</td>
        </tr>
      </router-link>
      <tr v-if="awaitingReviews.length === 0">
        <td colspan="4" style="text-align: center; font-style: italic" class="mid-section">geen reviews in afwachting ¯\_(ツ)_/¯</td>
      </tr>
      </tbody>
    </table>
    <table id="old-review-table">
        <caption>gesloten reviews</caption>
        <thead hide>

        <tr>
          <td>type</td>
          <td>naam</td>
          <td>competentie</td>
          <td>persoon</td>
        </tr>
        </thead>
        <tbody>
        <router-link
            v-for="(review, index) in completedReviews"
            v-bind:key="review"
            custom
            v-slot="{ isActive, href, navigate }"
            :to="`/form/${review.id}/done`"
        >
          <tr
              tabindex="0"
              :href="`/form/${review.id}/done`"
              @click="navigate"
              :class="index === completedReviews.length-2 ? 'observable' : ''"
              :id="index"
          >
            <td class="template-image-container" style="width: 5%"><img :src="`/img/${review.templateName}.png`" class="invert-image-color" @dragstart.prevent="(e) => e.preventDefault()" style="max-height: 100%" :alt="review.templateName" :title="review.templateName"></td>
            <td>{{ review.name }}</td>
            <td v-if="review.mainCompetence !== null">{{ `${review.mainCompetence} ${review.subCompetence} ${review.level}` }}</td>
            <td v-else></td>
            <td>{{ review.studentName }}</td>
          </tr>
        </router-link>
        </tbody>
      </table>

  </div>

</template>

<script>
import fetchAuth from "@/utils/authrequest";
import {useObservable} from "@vueuse/rxjs/index";
import {liveQuery} from "dexie";
import {db} from "@/db/db";

const SCROLL_SIZE = 10;
export default {
  name: "Reviews",
  computed: {
    view() {
      return localStorage.getItem('role');
    }
  },
  data() {
    return {
      completedReviews: [],
      pendingReviews: [],
      awaitingReviews: [],
      oldReviews: [],
      oldPageIndex: 0,
      maxOldPageIndex: Infinity,
      observer: new IntersectionObserver(this.resetObserver, {root:document.getElementById("#old-review-table"), threshold:0.25}),
    }
  },
  mounted() {
    fetchAuth("/api/review/pending")
      .then(response => response.json())
      .then(json => {
        this.pendingReviews = json
      })
    if (localStorage.getItem('role') !== 'ROLE_TEACHER') {
      fetchAuth("/api/review/awaiting")
          .then(response => response.json())
          .then(json =>  {
            this.awaitingReviews = json;
          })
    }
    console.log(db.completedReviews.orderBy('finishedAt').first());
    db.completedReviews.orderBy('finishedAt').reverse().first(async (timeStamp) => {
      let request;
      if (!timeStamp) {
        request = fetchAuth('/api/review/old')
            .then(response => response.json())
            .then(json => {
              db.completedReviews.bulkPut(json);
            })
      } else {
        request = fetchAuth(`/api/review/old?from=${timeStamp.finishedAt}`)
            .then(response => response.json())
            .then(json => {
              db.completedReviews.bulkPut(json);
            })
      }
      request.then(() => {
        db.completedReviews.orderBy('finishedAt').reverse().limit(SCROLL_SIZE).toArray(result => this.completedReviews = result)
      }).catch(() => {
        db.completedReviews.orderBy('finishedAt').reverse().limit(SCROLL_SIZE).toArray(result => this.completedReviews = result)
      });

    })
    
    // fetchAuth(`/api/review/old`)
    //     .then(response => response.json())
    //     .then(json =>  {
    //       if (json.length < SCROLL_SIZE) this.maxOldPageIndex = 0;
    //       this.oldReviews = json;
    //     })

  },
  updated() {


    const observables = document.getElementsByClassName("observable");
    console.log(observables)
    if (observables.length > 0) {
      this.observer.observe(observables[observables.length-1])
    }

  },
  methods: {
    nextOld() {
      console.log('observed')
      this.oldPageIndex++;
      db.completedReviews.orderBy('finishedAt').reverse().offset(SCROLL_SIZE * this.oldPageIndex).limit(SCROLL_SIZE).toArray((json => {
        if (this.oldPageIndex >= this.maxOldPageIndex) return;

        if (json.length === 0) {
          this.maxOldPageIndex = this.oldPageIndex;
        } else {
          if (json.length < SCROLL_SIZE) this.maxOldPageIndex = 0;

          this.completedReviews = [...this.completedReviews, ...json];
        }
      }));
      // const json = useObservable(liveQuery(() => db.completedReviews.orderBy('finishedAt').reverse().offset(SCROLL_SIZE * this.oldPageIndex).limit(SCROLL_SIZE).toArray()))

    },
    resetObserver(entries, observer) {
      if(!entries[0].isIntersecting) return;
      observer.unobserve(entries[0].target)
      this.nextOld();
    },
  },
}
</script>

<style src="@/assets/css/table.less" />

<style lang="less">
.review-view-wrapper {
  width: 100vw;

  > table {
    margin-top: 2em;
    &:first-child {
      margin-top: 0;
    }
  }
}
</style>