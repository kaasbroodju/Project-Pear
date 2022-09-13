<template>
  <div style="width: 100vw">
    <h1>{{ this.$route.params.groupName }}</h1>
    <table>
      <caption>studenten</caption>
      <thead hide>
      <tr>
        <td>name</td>
      </tr>
      </thead>
      <tbody>
      <router-link
          v-for="student in students"
          v-bind:key="students"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/student/${student.email}/details`"
      >
        <tr @click="navigate">
          <td>{{ student.name }}</td>
        </tr>
      </router-link>
      </tbody>
    </table>
    <table>
      <caption>formulier samenvattingen</caption>
      <thead hide>
      <tr>
        <td>name</td>
      </tr>
      </thead>
      <tbody>
      <router-link
          v-for="form in forms"
          v-bind:key="form"
          custom
          v-slot="{ isActive, href, navigate }"
          :to="`/group/${this.$route.params.groupName}/${form}/details`"
      >
        <tr @click="navigate">
          <td>{{ form }}</td>
        </tr>
      </router-link>
      </tbody>
    </table>
  </div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "InspectGroup",
  data() {
    return {
      students: [],
      forms: [],
    }
  },
  mounted() {
    fetchAuth(`/api/teacher/group/${this.$route.params.groupName}/student`).then(response => response.json()).then(result => this.students = result)
    fetchAuth(`/api/teacher/formtemplate`).then(response => response.json()).then(result => this.forms = result)

  }
}
</script>

<style src="@/assets/css/table.less" />

<style scoped>

</style>