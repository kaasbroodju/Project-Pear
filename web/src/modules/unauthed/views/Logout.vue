<template>

</template>

<script>
import {db} from "@/db/db";

export default {
  name: "Logout",
  mounted() {
    localStorage.clear()
    db.delete();
    this.$router.push({path: "/"})
  }
}
</script>

<style scoped>

</style>