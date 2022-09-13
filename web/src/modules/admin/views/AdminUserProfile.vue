<template>
  <form class="admin-user-profile" @submit.prevent="updateUser">
    <div class="image-name-header">
      <img src="@/assets/temp_dev_user.png" alt="">
      <h1 contenteditable @input="changed = true" ref="name">{{ name }}</h1>
    </div>
    <div class="image-name-header">
      <h2>{{ email }}</h2>
      <label v-if="role === 'Student' || role === 'Apprentice'">
        is student an apprentice
        <input type="checkbox" ref="isApprentice" :checked="role === 'Apprentice'" @change="this.changed = true">
      </label>
      <h2 v-else class="float-right">role: {{ role }}</h2>
    </div>
    <div class="badge-section">
      <h2>badges</h2>
      <ul>
        <li v-for="badge in badges">
          <label>
            <input type="checkbox" ref="badges" :checked="userBadges.includes(badge)" @change="this.changed = true" :value="badge">
            {{ badge }}
          </label>
        </li>

      </ul>
    </div>
    <div>
      <button type="submit" class="update" :disabled="!changed">update user</button>
      <button type="button" class="delete">delete user</button>
    </div>
  </form>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "AdminUserProfile",
  data() {
    return {
      changed: false,
      email: "",
      name: "",
      badges: [],
      userBadges: [],
      role: "",
    }
  },
  mounted() {
    fetchAuth(`/api/admin/user/${this.$route.params.email}`)
        .then(response => response.json())
        .then(json => {
          this.name = json.name;
          this.email = json.email;
          this.userBadges = json.badges;
          this.role = json.role
        })
    fetchAuth("/api/admin/badge")
        .then(response => response.json())
        .then(badges => this.badges = badges)
  },
  methods: {
    updateUser() {
      const data = {}
      data.name = this.$refs.name.innerText;
      if (this.$refs.isApprentice) data.isApprentice = this.$refs.isApprentice.checked;
      if (this.$refs.badges) {
        if (Array.isArray(this.$refs.badges)) {
          data.badges = this.$refs.badges.filter(e => e.checked).map(checkbox => checkbox.value);
        } else {
          data.badges = this.$refs.badges.checked ? [this.$refs.badges.value] : [];
        }
      } else {
        data.badges = [];
      }


      fetchAuth(`/api/admin/user/${this.$route.params.email}`, {method: "PATCH", body: JSON.stringify(data), headers: {'Content-Type': 'application/json'}})
    },
  }
}
</script>

<style scoped lang="less">
.admin-user-profile {
  min-height: 100vh;
  > div {
    padding: 1em;
    display: flex;
    img {
      width: 15vw;
    }
    &.badge-section {
      display: inherit;
    }

    ul {
      list-style: none;
      label {
        font-size: initial;
      }
    }

    label {
      margin-left: auto;
      font-size: 2em;
      > select {
        font-size: 1em;
      }
    }

    h2.float-right {
      margin-left: auto;
    }

  }

  .image-name-header {
    align-items: center;
    h1 {
      margin-left: 2em;
      font-size: 3em;
    }
  }
  div:last-child {
    position: absolute;
    bottom: 0;
    right: 0;

    button {
      margin: .5em;

      &.delete {
        background-color: red;
        &:focus, &:hover {
          background-color: darkred;
        }
      }

      &.update:not(:disabled) {
        background-color: blue;
        &:focus, &:hover {
          background-color: darkblue;
        }
      }

      color: white;

    }
  }
}



</style>