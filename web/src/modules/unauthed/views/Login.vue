<template>
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
      <router-link to="/signup">don't have an account?</router-link>
    </form>
  </anonymous-form>
</template>

<script>
import AnonymousForm from "@/components/AnonymousForm.vue";
import jwtDecode from "jwt-decode";
import {db} from "@/db/db";

export default {
  name: "Login",
  components: {AnonymousForm},
  methods: {
    login(e) {
      fetch("/api/login", {method:"POST", body:new FormData(e.target)})
          .then(response => {if (response.ok) return response.json(); throw "failed to login"})
          .then(tokens => {
            localStorage.setItem("accessToken", "Bearer " + tokens.accessToken);
            localStorage.setItem("refreshToken", "Bearer " + tokens.refreshToken);
            const role = jwtDecode(tokens.accessToken).roles[0];
            if (role) localStorage.setItem("role", role)
            if (role === "ROLE_ADMIN") {
              this.$router.push({ path: "/admin/user"})
            } else {
              db.open();
              this.$router.push({ path: "/home"})
            }

          })
          .catch(() => {

          })
    }
  }
}
</script>

<style scoped>

</style>