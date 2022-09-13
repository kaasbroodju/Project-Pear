<template>
  <div>
    <h1>{{ this.$route.params.name }}</h1>
    <div class="person-wrapper">
      <div>
        <h2>students</h2>
        <input type="text" placeholder="search student" @change="searchStudents">
        <table v-show="studentsSearchResults.length > 0">
          <caption>found students</caption>
          <thead hide>
          <tr>
            <td>name</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="student in studentsSearchResults">
            <td>{{ student.name }}</td>
            <td @click="addStudentToGroup(student)">add</td>
          </tr>
          </tbody>
        </table>
        <table>
          <caption>students</caption>
          <thead hide>
            <tr>
              <td>name</td>
            </tr>
          </thead>
          <tbody>
            <tr v-for="student in students">
              <td>{{ student.name }}</td>
              <td @click="removeStudentFromGroup(student)">remove</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div>
        <h2>coach</h2>
        <select @change="(e) => changeCoachTo(e.target.value)">
          <option :selected="coach.email === ''" value="">--please select a value--</option>
          <option v-for="teacher in teachers" :value="teacher.email" :selected="coach.email === teacher.email">{{ teacher.name }}</option>
        </select>
      </div>
    </div>
    <button type="button" class="delete" @click="deleteGroup">delete group</button>
  </div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "AdminGroupProfile",
  data() {
    return {
      coach: {email: ''},
      studentsSearchResults: [],
      students: [],
      teachers: [],
    }
  },
  mounted() {
    fetchAuth(`/api/admin/group/${this.$route.params.name}`)
        .then(response => response.json())
        .then(group => {
          if (group.coach) this.coach = group.coach;
          this.students = group.students
        })
    fetchAuth(`/api/admin/teacher`)
        .then(response => response.json())
        .then(teachers => {
          this.teachers = teachers;
        })
  },
  methods: {
    searchStudents(e) {
      fetchAuth(`/api/admin/student/search/${e.target.value}`)
          .then(response => response.json())
          .then(students => {
            const studentEmails = this.students.map(student => student.email);
            this.studentsSearchResults = students.filter(student => !studentEmails.includes(student.email))
          })
    },
    addStudentToGroup(student) {
      fetchAuth(`/api/admin/group/${this.$route.params.name}/student/${student.email}`, {method:"POST"})
          .then(() => {
            this.students.push(student)
            this.studentsSearchResults = []
          })
    },
    removeStudentFromGroup(student) {
      fetchAuth(`/api/admin/group/${this.$route.params.name}/student/${student.email}`, {method:"DELETE"})
          .then(() => {
            this.students = this.students.filter(s => s.email !== student.email)
          })
    },
    changeCoachTo(coach) {
      if (coach === '') return;
      fetchAuth(`/api/admin/group/${this.$route.params.name}/coach/${coach}`, {method:"PATCH"})
    },
    deleteGroup() {
      fetchAuth(`/api/admin/group/${this.$route.params.name}`, {method:"DELETE"})
          .then(() => this.$router.push({path:'/admin/group'}))
    }
  }
}
</script>

<style src="@/assets/css/table.less"></style>


<style scoped lang="less">
.person-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 1em;

  input[type="text"] {
    width: 100%;
  }
}


button {
  margin: .5em;
  position: absolute;
  bottom: 0;
  right: 0;

  &.delete {
    background-color: red;

    &:focus, &:hover {
      background-color: darkred;
    }
  }
}
</style>