<template>
  <overlay-div required custom-wrapper ref="overlay">
    <anonymous-form>
      <form class="login-form" @submit.prevent="login">
        <label>
          email:
          <br>
          <input type="email" name="email" required>
        </label>
        <label>
          password:
          <br>
          <input type="password" name="password" required>
        </label>
        <button type="submit">log in</button>
      </form>
    </anonymous-form>

  </overlay-div>
</template>

<script>
import OverlayDiv from "@/components/OverlayDiv.vue";
import AnonymousForm from "@/components/AnonymousForm.vue";
export default {
  name: "LoginOverlay",
  components: {AnonymousForm, OverlayDiv},
  mounted() {
    window.showLoginOverlay = () => {this.$refs.overlay.showOverlay()}
  },
  methods: {
    login(e) {
      fetch("/api/login", {method:"POST", body:new FormData(e.target)})
          .then(response => {if (response.ok) return response.json(); throw "failed to login"})
          .then(tokens => {
            localStorage.setItem("accessToken", "Bearer " + tokens.accessToken);
            localStorage.setItem("refreshToken", "Bearer " + tokens.refreshToken);
            this.$refs.overlay.hideOverlay();


          })
          .catch(() => {

          })
    }
  }
}
</script>

<style scoped lang="less">
.div-overlay {
  z-index: 1001;
}
</style>