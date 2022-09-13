<template>
  <nav>
    <router-link to="/admin/user">users</router-link>
    |
    <router-link to="/admin/group">groups</router-link>
    |
    <router-link to="/logout">log out</router-link>
    |
  </nav>
</template>

<script>
export default {
  name: "AdminNavBar"
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
</style>