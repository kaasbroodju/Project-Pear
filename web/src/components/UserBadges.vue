<template>
  <ul>
    <li v-for="(value, key) in badges">
      {{ key }}
      <ul>
        <li v-for="badge in value">
          <label>
            <input type="checkbox" :checked="userBadges.includes(badge)" @change="updateUserBadge" :name="badge">
            {{ badge }}
          </label>
        </li>

      </ul>

    </li>
  </ul>

</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "UserBadges",
  data() {
    return {
      badges: new Map(),
      userBadges: []
    }
  },
  mounted() {
    fetchAuth("/api/badge/group").then(response => response.json()).then(json => this.badges = json)
    fetchAuth("/api/badge").then(response => response.json()).then(json => this.userBadges = json)

  },
  methods: {
    updateUserBadge(e) {
      fetchAuth(`/api/badge/user/${e.target.attributes.name.value}`, {method: e.target.checked ? "POST":"DELETE"})
    }
  }
}
</script>

<style scoped>
ul {
  list-style: none;
}

li > ul {
  padding-left: 1rem;
}

</style>