<template>
  <login-overlay />
  <template v-if="view === 'ROLE_TEACHER'">
    <teacher-nav-bar />
  </template>

  <template v-else>
    <nav-bar/>
  </template>

  <router-view v-slot="{ Component }">
    <transition :name="animationName">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import LoginOverlay from "@/components/LoginOverlay.vue";
import TeacherNavBar from "@/modules/teacher/components/TeacherNavBar.vue";

const NAVLINKS = ["/home", "/old", "/reviews", "/progress", "/drafts", "/board", "/settings"];

export default {
  name: "MainRoute",
  components: {TeacherNavBar, LoginOverlay, NavBar},
  data() {
    return {
      animationName: '',
    }
  },
  computed: {
    view() {
      return localStorage.getItem('role');
    }
  },
  watch: {
    '$route': {
      handler(to, from) {

        if (from.path === "/") {
          // slide from right to left always when navigating to home
          this.animationName = '';
          return to;
        }

        if (to.path === "/home") {
          // slide from right to left always when navigating to home
          this.animationName = 'reverse-slide';
          return to;
        }

        if (from.path === "/home" && to.path.startsWith("/group")) {
          // slide from right to left always when navigating to home
          this.animationName = 'slide';
          return to;
        } else if (from.path.endsWith("/details") && to.path.startsWith("/group")) {
          this.animationName = 'reverse-slide';
          return to;
        }

        if (to.path.endsWith("/details")) {
          // slide from right to left always when navigating to home
          if (to.path.startsWith("/group") || to.path.startsWith("/student")) {
            this.animationName = 'slide';
          } else {
            this.animationName = 'reverse-slide';
          }
          return to;
        }
        const toIndex = NAVLINKS.indexOf(to.path);
        const fromIndex = NAVLINKS.indexOf(from.path);

        if (to.path.startsWith("/form") && fromIndex >= 0) {
          // slide up when from main navbar elements to a form fill in
          this.animationName = 'slide-up';
        } else if (from.path.startsWith("/form") && toIndex >= 0) {
          // slide down when from a form fill in to a main navbar element
          this.animationName = 'slide-down'
        } else if (toIndex > fromIndex) {
          // slide from right to left
          this.animationName = 'slide';
        } else if (fromIndex > toIndex) {
          // slide from left to right
          this.animationName = 'reverse-slide'
        } else {
          // no animation
          this.animationName = '';
        }


      }
    }
  },
}
</script>

<style scoped>

</style>