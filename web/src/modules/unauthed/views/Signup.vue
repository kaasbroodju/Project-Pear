<template>
  <anonymous-form>
    <form @submit.prevent="signup" v-if="!email">
      <label>
        email:
        <input type="email" name="email" pattern="[a-z]+\.[a-z]+@(?:hu.nl|student.hu.nl)" required>
      </label>
      <button type="submit">sign up</button>
      <router-link to="/login">I already have an account</router-link>
    </form>
    <div v-else>
      <p>Email has been sent to: {{ email }}</p>
      <router-link to="/login">go to login</router-link>
    </div>

  </anonymous-form>

</template>

<script>
import AnonymousForm from "@/components/AnonymousForm.vue";

export default {
  name: "Signup",
  components: {AnonymousForm},
  data() {
    return {
      email: undefined
    }
  },
  methods: {
    signup(e) {
      fetch(`/api/register/${new FormData(e.target).get("email")}`, {method:"POST"}).then(() => this.email = new FormData(e.target).get("email"))
    }
  }
}
</script>

<style scoped>

</style>