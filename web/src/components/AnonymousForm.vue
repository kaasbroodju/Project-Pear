<template>
  <div class="login-page-wrapper">
    <div class="login-wrapper">
      <div class="login-header">
        <img src="@/assets/logo.png">
        <p>Project Pear</p>
      </div>
      <div class="login-content">
        <slot></slot>
      </div>

    </div>
  </div>

</template>

<script>
export default {
  name: "AnonymousForm"
}
</script>

<style scoped lang="less">
.login-page-wrapper {
  display: grid;
  align-content: center;
  justify-content: center;
  min-height: 100vh;


  .login-wrapper {
    border-radius: 2em;
    padding: .5em 1em;
    background-color: var(--background-soft);
    width: 50vw;
    display: unset;
    font-size: 2em;

    .login-header {
      display: flex;
      align-items: center;
      justify-content: center;
      img {
        width: 200px;
      }
      p {
        font-size: 5rem;
      }
    }

    .login-content:deep(form) {


      input {
        font-size: 1em;
        width: 100%;
        margin-bottom: 1em;
      }

      button {
        float: right;
      }

      //a {
      //  float: right;
      //}
    }


  }


}
</style>