<template>
  <div>
    <overlay-div ref="overlay">
      <form class="review-overlay" @submit.prevent="submitForm">
        <h2>{{dialogUser.name}}</h2>
        <input type="hidden" name="reviewerEmail" :value="dialogUser.email">
        <div>
          <badge v-for="badge in dialogUser.badges" v-bind:key="badge" :name="badge"/>
        </div>

        <div class="degree-assessment-wrapper">
          <template v-for="type of dialogUser.reviewTypes" v-bind:key="type">
            <image-radio-input :src="`/img/${type.toLowerCase()}.png`" :text="type"></image-radio-input>
          </template>
        </div>
        <br>
        <button type="submit">submit</button>
      </form>
    </overlay-div>
    <div v-if="dialogUser" id="reviewerDialog" @click="this.dialogUser = undefined">

    </div>
    <h1>Add reviewer</h1>

    <div @focusout="closeSearchResults">
      <input type="text" placeholder="search" ref="searchBar" class="search-reviewer-bar" data-pear-do-not-leave @input="search" @focusin="showPossibleSearchResults" @focusout.stop @blur="closeSearchResults"/>
      <div v-if="searchResults.length > 0" class="search-result-list-wrapper" data-pear-do-not-leave >
        <ul class="search-result-list" >
          <li v-for="(person, i) in searchResults" v-bind:key="person" @click="showDialogWithReviewer(person)" tabindex="0" @focusout="(e) => {if (i < searchResults.length-1) {e.stopPropagation();} }" data-pear-do-not-leave>
            <favourite-button
                :name="person.name"
                :email="person.email"
                :favourite="favourites.has(person.email)"
                @add-favourite="addFavourite"
                @remove-favourite="removeFavourite"
                @click="(e) => e.stopPropagation()"
                data-pear-do-not-leave
            />
            <p data-pear-do-not-leave>{{person.name}}</p>
          </li>
        </ul>
        <div class="search-reviewer-footer-bar"/>
      </div>

    </div>


    <div class="reviewer-wrapper">

      <div class="tab">
        <button :class="`tablinks ${selectedTab === 'favourites' ? 'active' : ''}`" @mouseover="changeState('favourites')" @focus="changeState('favourites')">Favorieten</button>
        <button :class="`tablinks ${selectedTab === 'group' ? 'active' : ''}`" @mouseover="changeState('group')" @focus="changeState('group')">Mijn groep</button>
        <button :class="`tablinks ${selectedTab === 'teacher' ? 'active' : ''}`" @mouseover="changeState('teachers')" @focus="changeState('teachers')">Docenten</button>
        <button :class="`tablinks ${selectedTab === 'apprentices' ? 'active' : ''}`" @mouseover="changeState('apprentices')" @focus="changeState('apprentices')">Apprentices</button>
        <button :class="`tablinks ${selectedTab === key ? 'active' : ''}`" @mouseover="changeState(key)" @focus="changeState(key)" v-for="[key] in guilds" v-bind:key="key">
          {{ key }}
        </button>
        <button :class="`tablinks ${selectedTab === 'extern' ? 'active' : ''}`" @mouseover="changeState('extern')" @focus="changeState('extern')">Extern</button>

      </div>

      <div>
        <table v-if="selectedTab === 'favourites'">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="person in [...favourites.values()]" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
          </tr>
          </tbody>
        </table>
        <table v-else-if="selectedTab ==='group'">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="person in myGroup" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
          </tr>
          </tbody>
        </table>
        <table v-else-if="selectedTab ==='teachers'">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
            <td>gilde</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="person in teachers" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.guildName }}
            </td>
          </tr>
          </tbody>
        </table>
        <table v-else-if="selectedTab ==='apprentices'">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
            <td>gilde</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="person in apprentices" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.guildName }}
            </td>
          </tr>
          </tbody>
        </table>
        <table v-else-if="guilds.has(selectedTab)">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td colspan="2" class="mid-section">Gilde meesters</td>
          </tr>

          <tr v-for="person in guilds.get(selectedTab).guildMasters" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
          </tr>

          <tr>
            <td colspan="2" class="mid-section">Apprentices</td>
          </tr>

          <tr v-for="person in guilds.get(selectedTab).apprentices" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
          </tr>


          <tr>
            <td colspan="2" class="mid-section">Leden</td>
          </tr>
          <tr v-for="person in guilds.get(selectedTab).students" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
          </tr>
          </tbody>
        </table>
        <table v-else-if="selectedTab ==='extern'">
          <thead hide>
          <tr>
            <td>favourite</td>
            <td>name</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="person in extern" v-bind:key="person">
            <td>
              <favourite-button
                  :name="person.name"
                  :email="person.email"
                  :favourite="favourites.has(person.email)"
                  @add-favourite="addFavourite"
                  @remove-favourite="removeFavourite"
              />
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.name }}
            </td>
            <td @click="showDialogWithReviewer(person)">
              {{ person.company }}
            </td>
          </tr>
          </tbody>
        </table>
      </div>


    </div>
    <router-link
        id="addToBoard"
        :to="`/form/reviewer/${this.$route.params.id}/board`">add to open board</router-link>
  </div>


</template>

<script>
import PossibleReviewer from "@/modules/formelements/components/PossibleReviewer.vue";
import OverlayDiv from "@/components/OverlayDiv.vue";
import Badge from "@/components/Badge.vue";
import FavouriteButton from "@/components/FavouriteButton.vue";
import fetchAuth from "@/utils/authrequest";
import ImageRadioInput from "@/components/ImageRadioInput.vue";

export default {
  name: "AddReviewer",
  components: {ImageRadioInput, FavouriteButton, Badge, OverlayDiv, PossibleReviewer},
  data() {
    return {
      selectedTab: "favourites",
      myGuild: "back end",
      guilds: new Map(),
      favourites: new Map(),
      searchResults: [],
      apprentices: [],
      guildNames: [],
      teachers: [],
      showDialog: false,
      dialogUser: undefined,
      myGroup: [
        {name: "Hutspot", email: "hut.spot@student.hu.nl"}
      ],
      extern: [
        {name: "Morris Waaijer", email: "hut.spot@student.hu.nl", company: "OEP"}
      ],
    }
  },
  beforeMount() {
    fetchAuth("/api/favourite")
        .then(result => result.json())
        .then(json => {
          for (let person of json) {
            this.favourites.set(person.email, person);
          }
        })
    fetchAuth("/api/guild")
        .then(result => result.json())
        .then(json => {
          this.myGuild = json.name;
          this.guilds.set(json.name, json);
        })
    fetchAuth("/api/apprentice")
        .then(result => result.json())
        .then(json => {
          this.apprentices = json;
        })
    fetchAuth("/api/teacher")
        .then(result => result.json())
        .then(json => {
          this.teachers = json;
        })
    fetchAuth("/api/guild/name/all")
        .then(response => response.json())
        .then(json => {
          this.guildNames = json;
          for (let guildName of json) {
            if (!this.guilds.has(guildName)) this.guilds.set(guildName, {});
          }
        });
  },
  methods: {
    changeState(state) {
      this.selectedTab = state;
      if (!this.guildNames.includes(state)) return;
      if (Object.keys(this.guilds.get(state)).length === 0) {
        fetchAuth("/api/guild/"  + state)
            .then(result => result.json())
            .then(json => {
              this.guilds.set(json.name, json);
            })
      }
    },
    addFavourite(e) {
      this.favourites.set(e.email, e);
    },
    removeFavourite(e) {
      this.favourites.delete(e.email);
    },
    async showDialogWithReviewer(e) {

      const typeReviews = fetchAuth("/api/review/type/" + e.email).then(result => [result.ok, result.json()]).then(([ok, json])=> {
        return ok ? json : [];
      })
      const badges = fetchAuth("/api/badge/" + e.email).then(result => [result.ok, result.json()]).then(([ok, json])=> {
        return ok ? json : [];
      })
      const user = await Promise.all([typeReviews, badges])
      this.dialogUser = { reviewTypes: user[0], badges: user[1], ...e}
      this.$refs.overlay.showOverlay();
      if (this.$refs.searchBar.nextElementSibling) this.$refs.searchBar.nextElementSibling.style.display = 'none'
    },
    submitForm(e) {
      fetchAuth("/api/review/" + this.$route.params.id, {
        method: "post",
        body: this.serializeAndJsonify(new FormData(e.target)),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => response.status).then(status => {
        if (status === 200) {
          this.$refs.overlay.hideOverlay();
          window.scrollTo(0, 0);
          this.$router.push({path: "/home"})
        }
      })
    },
    search(e) {
      if (e.target.value.length < 3) {
        this.searchResults = [];
        return;
      }
      this.showPossibleSearchResults();
      fetchAuth(`/api/review/search/${e.target.value}`).then(response => response.json()).then(results => this.searchResults = results)
    },
    showPossibleSearchResults() {
      if(this.$refs.searchBar.nextElementSibling) this.$refs.searchBar.nextElementSibling.style.display = ''
    },
    closeSearchResults(e) {
      if (e.type === "blur") {
        if (e.explicitOriginalTarget.hasAttribute) {
          if (e.explicitOriginalTarget.hasAttribute("data-pear-do-not-leave")) {
            return;
          } else {
            if (e.explicitOriginalTarget.parentElement) {
              if (e.explicitOriginalTarget.parentElement.hasAttribute("data-pear-do-not-leave")) {
                return;
              }
            }
          }
        } else {
          if (e.explicitOriginalTarget.parentElement) {
            if (e.explicitOriginalTarget.parentElement.hasAttribute("data-pear-do-not-leave")) {
              return;
            }
          }
        }
      }

      this.$refs.searchBar.nextElementSibling.style.display = 'none'
    },
    serializeAndJsonify(formData) {
      let obj = {};
      for (const key of formData.keys()) {
        obj[key] = formData.get(key);
      }
      return JSON.stringify(obj);
    }
  }
}
</script>

<style scoped lang="less">
@import "@/assets/css/table.less";

table tbody tr td:first-child {
  width: 0;
  padding: 0 1em;
}
.review-overlay {
  text-align: center;
}
.search-reviewer-bar {
  width: calc(100% - 8px);
  font-size: 2em;
  z-index: 1;
  position: relative;

  //&:not(:focus-within) + div {
  //  display: none;
  //}
}
.reviewer-wrapper {
  display: grid;
  grid-template-columns: 20vw 80vw;
  min-height: calc(100vh - 5.375em);
  grid-auto-rows: 1fr;

  .tab {
    background-color: var(--background-sidebar);
    display: flex;
    flex-direction: column;
  }

}

#addToBoard {
   position: fixed;
   right: 20px;
   bottom: 20px;
   background: blue;
   border-radius: 20px;
   padding: 10px;
   color: white;
   text-decoration: none;
 }
/* Style the buttons that are used to open the tab content */
.tab button {
  display: block;
  background-color: inherit;
  padding: 22px 16px;
  width: 100%;
  border: none;
  outline: none;
  text-align: left;
  cursor: pointer;
  font-weight: bold;
  font-size: 1em;
  border-top-right-radius: 1em;
  border-bottom-right-radius: 1em;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: var(--background);
}

/* Create an active/current "tab button" class */
.tab button.active {
  background-color: var(--background);
}
.search-result-list-wrapper {
  position: absolute;
  .search-result-list {
    list-style: none;

    z-index: 1;
    background-color: var(--background);
    width: 100vw;
    margin: 0;
    padding: 0;



    li {
      font-size: 1.25em;
      padding-top: 1em;
      padding-bottom: 1em;
      padding-left: 1em;
      display: flex;
      align-items: center;

      .switch {
        margin-right: .5em;
      }

      p {
        margin: 0;
      }

      &:not(:last-child) {
         border-bottom: 1px solid var(--text-soft);
       }

      &:hover, &:focus {
        background-color: var(--background-soft);
      }
    }
  }

  .search-reviewer-footer-bar {
    background-color: var(--background-sidebar);
    height: 2em;
    width: 100vw;

  }
}


/* Style the tab content */
//.tabcontent {
//  float: left;
//  padding: 0px 12px;
//  width: calc(70% - 26px);
//  border-left: none;
//  height: 300px;
//  display: block;
//  text-align: left;
//
//  li {
//    display: flex;
//  }
//}
.degree-assessment-wrapper {
  display: flex;
  justify-content: center;
}
</style>