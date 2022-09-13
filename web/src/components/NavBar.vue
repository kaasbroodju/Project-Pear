<template>
  <nav id="nav">
    <router-link to="/home">Home</router-link>
    <span>|</span>
    <router-link to="/old">Old</router-link>
    <span>|</span>
    <router-link to="/reviews">{{pendingReviews === 0  ? 'Reviews' : 'Reviews '}}<p v-if="pendingReviews > 0" class="pending-reviews">{{ pendingReviews }}</p></router-link>
    <span>|</span>
    <router-link to="/progress">Progress</router-link>
    <span>|</span>
    <router-link to="/drafts">Drafts</router-link>
    <span>|</span>
    <router-link to="/board">Board</router-link>
    <span>|</span>
    <router-link to="/settings">Settings</router-link>
    <span>|</span>
    <div class="nav-bar-dropdown">
      <button class="dropbtn">miscellaneous</button>
      <div class="nav-bar-dropdown-content">
        <a href="https://github.com/kaasbroodju/Project-Pear" target="_blank">Github</a>
        <router-link to="/api/swagger-ui.html" target="_blank">Api docs</router-link>
        <a href="https://openict.fyndr.wiki/" target="_blank">Fyndr</a>
        <a href="https://competenties.hu-open-ict.nl/" target="_blank">Competenties</a>
      </div>

    </div>

    <span>|</span>
    <router-link to="/logout">Log out</router-link>


  </nav>
</template>
<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: 'NavBar',
  data() {
    return {
      pendingReviews: 0,
    }
  },
  beforeMount() {
    fetchAuth("/api/review/pending/amount").then(response => response.text()).then(number => this.pendingReviews = Number.parseInt(number))
  }
}
</script>
<style scoped>

#nav {
  padding: 2rem;
  background-color: var(--background-soft);
  text-align: center;
}

#nav a {
  font-weight: bold;
  text-decoration: none;
  color: var(--text);
  padding: 0 .25rem;
}

a {
  position: relative;
}

a::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 4px;
  border-radius: 4px;
  background-color: var(--text);
  bottom: -4px;
  left: 0;
  transform-origin: center;
  transform: scaleX(0);
  transition: transform .3s ease-in-out;
}

a:hover::before {
  transform-origin: center;
  transform: scaleX(1);
}

.pending-reviews {
  display: inline;
  width: 0;
  background-color: var(--background-sidebar);
  border-radius: 25%;
}


/* Style The Dropdown Button */
.dropbtn {
  background-color: var(--background-soft);
  color: var(--text);
  padding: 0 0.25em;
  font: inherit;
  font-weight: bold;
  /*font-size: 16px;*/
  border: none;
  cursor: pointer;
}

/* The container <div> - needed to position the dropdown content */
.nav-bar-dropdown {
  position: relative;
  display: inline-block;
}

/* Dropdown Content (Hidden by Default) */
.nav-bar-dropdown-content {
  display: none;
  position: absolute;
  background-color: var(--background-soft);
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  right: -20px;
}

/* Links inside the dropdown */
.nav-bar-dropdown-content a {
  /*color: black;*/
  padding: 12px 16px;
  margin: 10px;
  /*text-decoration: none;*/
  display: block;
}

/* Change color of dropdown links on hover */
/*.nav-bar-dropdown-content a:hover {background-color: #f1f1f1}*/

/* Show the dropdown menu on hover */
.nav-bar-dropdown:hover .nav-bar-dropdown-content {
  display: block;
}

/* Change the background color of the dropdown button when the dropdown content is shown */
/*.nav-bar-dropdown:hover .dropbtn {*/
/*  background-color: var(--text-soft)*/
/*}*/

</style>