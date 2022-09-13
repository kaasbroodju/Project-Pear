<template>
  <div class="home">
    <div class="home-sidebar">
      <user-details />
    </div>
    <div class="home-create-form">
      <groups/>
    </div>
  </div>
</template>

<script>
import UserDetails from "@/components/UserDetails.vue";
import Groups from "@/modules/teacher/components/Groups.vue";
export default {
  name: "TeacherHome",
  components: {Groups, UserDetails}
}
</script>

<style scoped lang="less">
.home {
  display: grid;
  grid-template-columns: 30vw 70vw;
  min-height: calc(100vh - 5.375em);
  grid-auto-rows: 1fr;

  .home-sidebar {
    background-color: var(--background-sidebar);
  }

  .home-create-form {
    //display: grid;
    //grid-template-columns: 1fr 1fr 1fr;

    //padding: 10px;

    //> button {
    //  margin: 10px;
    //}
  }
}
</style>