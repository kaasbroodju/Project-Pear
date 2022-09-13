<template>
  <table>
    <caption>mijn groepen</caption>
    <thead hide>
      <tr>
        <td>group name</td>
      </tr>
    </thead>
    <tbody>
    <router-link
        v-for="group in groups"
        v-bind:key="group"
        custom
        v-slot="{ isActive, href, navigate }"
        :to="`/group/${group}`"
    >
      <tr @click="navigate">
        <td>{{ group }}</td>
      </tr>
    </router-link>
    </tbody>
  </table>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "Groups",
  data() {
    return {
      groups: []
    }
  },
  mounted() {
    fetchAuth('/api/teacher/group').then(response => response.json()).then(result => this.groups = result)
  }
}
</script>

<style src="@/assets/css/table.less" />

<style scoped>

</style>