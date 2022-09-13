<template>
  <form class="email-wrapper" @submit.prevent="inviteUser">
    <input type="email" placeholder="email" name="email" @input="(e) => this.searchEmail = e.target.value" autocomplete="off">
    <select name="role">
      <option value="STUDENT">Student</option>
      <option value="APPRENTICE">Apprentice</option>
      <option value="TEACHER">Teacher</option>
      <option value="EXTERN">Extern</option>
    </select>
    <button type="submit">invite</button>
  </form>
  <table>
    <thead hide>
      <tr>
        <td>email</td>
        <td>name</td>
        <td>role</td>
      </tr>
    </thead>
    <tbody>
      <router-link
          v-for="user in usersFiltered"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/admin/user/${user.email}/profile`">
        <tr
            tabindex="0"
            @click="navigate"
        >
          <td>{{ user.email }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.role }}</td>
        </tr>
      </router-link>

    </tbody>
  </table>
</template>

<script>
import fetchAuth from "@/utils/authrequest";
import {formDataToJsonString} from "@/utils/utils";

export default {
  name: "AdminUsers",
  data() {
    return {
      users: [],
      searchEmail: ""
    }
  },
  computed: {
    usersFiltered() {
      return this.users.filter(user => user.email.startsWith(this.searchEmail))
    }
  },
  mounted() {
    fetchAuth("/api/admin/user")
        .then(response => response.json())
        .then(users => this.users = users)
  },
  methods: {
    inviteUser(e) {
      fetchAuth("/api/admin/register", {method: "POST", body: formDataToJsonString(new FormData(e.target)), headers: {'Content-Type': 'application/json'}})
          .then(response => response.json())
          .then(users => this.users = users)
    }
  }
}
</script>

<style src="@/assets/css/table.less"></style>

<style scoped lang="less">
.email-wrapper {
  display: flex;
  input {
    font-size: 1.485em;
    width: 80vw;
  }
  select {
    width: 10vw;
  }
  button {
    width: 10vw;
  }
}

</style>