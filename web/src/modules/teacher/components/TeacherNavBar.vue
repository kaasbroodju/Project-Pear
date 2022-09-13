<template>
  <nav>
    <router-link to="/home">Home</router-link>
    |
    <router-link to="/reviews">Reviews <p v-if="pendingReviews > 0" class="pending-reviews">{{ pendingReviews }}</p></router-link>
    |
    <router-link to="/settings">Settings</router-link>
    |
    <router-link to="/logout">Log out</router-link>
  </nav>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "TeacherNavBar",
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
nav {
  padding: 2em;
  background-color: var(--background-soft);
  text-align: center;
}

nav a {
  font-weight: bold;
  text-decoration: none;
  color: var(--text);
}

.pending-reviews {
  display: inline;
  width: 0;
  background-color: var(--background-sidebar);
  border-radius: 25%;
}
</style>