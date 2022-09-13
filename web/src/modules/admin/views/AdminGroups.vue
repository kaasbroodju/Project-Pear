<template>
  <form class="email-wrapper" @submit.prevent="createGroup">
    <input ref="groupName" type="text" placeholder="group name" name="groupName" @input="(e) => this.searchGroup = e.target.value" autocomplete="off">
    <button type="submit">create group</button>
  </form>
  <table>
    <thead hide>
    <tr>
      <td>group name</td>
    </tr>
    </thead>
    <tbody>
    <router-link
        v-for="name in groups"
        custom
        v-slot="{ isActive, href, navigate }"
        :to="`/admin/group/${name}/profile`">
      <tr
          tabindex="0"
          @click="navigate"
      >
        <td>{{ name }}</td>
      </tr>
    </router-link>

    </tbody>
  </table>
</template>

<script>
import fetchAuth from "@/utils/authrequest";
import {formDataToJsonString} from "@/utils/utils";

export default {
  name: "AdminGroups",
  data() {
    return {
      groups: [],
      searchGroups: ""
    }
  },
  mounted() {
    fetchAuth("/api/admin/group")
        .then(response => response.json())
        .then(groups => this.groups = groups)
  },
  methods: {
    createGroup() {
      fetchAuth(`/api/admin/group/${this.$refs.groupName.value}`, {method: "POST"})
          .then(() => {
            this.groups.push(this.$refs.groupName.value);
            this.$refs.groupName.value = ""
          })
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
    width: 90vw;
  }
  button {
    width: 10vw;
  }
}
</style>