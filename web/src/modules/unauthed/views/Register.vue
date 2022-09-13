<template>
  <anonymous-form>
    <form @submit.prevent="submit">
      <label>
        wachtwoord:
        <br>
        <input type="password" name="password" minlength="8" required>
      </label>
      <br>
      <label>
        herhaal wachtwoord:
        <br>
        <input type="password" name="repeatPassword" minlength="8" required>
      </label>
      <button type="submit">create account</button>
      <span v-if="invalid">passwords do not match</span>
    </form>
  </anonymous-form>

</template>

<script>
import AnonymousForm from "@/components/AnonymousForm.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "Register",
  components: {AnonymousForm},
  data() {
    return {
      invalid: false
    }
  },
  methods: {
    submit(e) {
      const formData = new FormData(e.target);
      if (formData.get("password") !== formData.get("repeatPassword")) {
        this.invalid = true;
        return;
      } else {
        this.invalid = false;
      }
      fetch(`/api/register/finish/${this.$route.params.id}`, {method: "POST", body: JSON.stringify({password:formData.get("password")}), headers: {'Content-Type':"application/json"}})
          .then(response => {
            if (response.ok) this.$router.push({path: "/login"})
          })
    }
  }
}
</script>

<style scoped>

</style>