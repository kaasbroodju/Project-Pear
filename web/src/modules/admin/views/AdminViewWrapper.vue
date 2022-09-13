<template>
  <div>
    <login-overlay></login-overlay>
    <admin-nav-bar></admin-nav-bar>
    <router-view></router-view>
  </div>

</template>

<script>
import OverlayDiv from "@/components/OverlayDiv.vue";
import LoginOverlay from "@/components/LoginOverlay.vue";
import AdminNavBar from "@/modules/admin/components/AdminNavBar.vue";
export default {
  name: "AdminViewWrapper",
  components: {AdminNavBar, LoginOverlay, OverlayDiv},
}
</script>

<style scoped>


</style>