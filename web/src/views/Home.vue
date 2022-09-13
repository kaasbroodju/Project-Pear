<template>
  <div>

    <template v-if="view === 'ROLE_TEACHER'">
      <teacher-home />
    </template>

    <template v-else>
      <student-home />
    </template>

  </div>
</template>

<script>
import TeacherHome from "@/modules/teacher/views/TeacherHome.vue";
import StudentHome from "@/views/StudentHome.vue";

export default {
  name: 'Home',
  components: {StudentHome, TeacherHome},
  computed: {
    view() {
      return localStorage.getItem('role');
    }
  }
}
</script>

<style scoped lang="less">

</style>
