<template>
  <transition
      name="pop-up"
  >

    <div id="popUp" v-if="message">
      <div :class="`pop-up-wrapper ${type}`">
        <div class="left"/>
        <p>
          {{ message }}
        </p>
        <div class="right"/>

      </div>
    </div>

  </transition>
</template>

<script>
export default {
  name: "BottomPopup",
  data() {
    return {
      message: undefined,
      type: undefined,
    }
  },
  mounted() {
    window.createNotification = (type, message) => this.showNotification(type, message);
  },
  methods: {
    showNotification(type, message) {

      this.type = type;
      this.message = message;
      setTimeout(this.resetData, this.getDisplayTime(type));
    },
    getDisplayTime(type) {
      switch (type) {
        case "error": return 8000;
        case "success": return 8000;
        case "info": return 4000;
        default: return 2000;
      }
    },
    resetData() {
      this.type = undefined;
      this.message = undefined;
    }
  }
}
</script>

<style scoped lang="less">

  #popUp {
    text-align: center;
    position: sticky;
    bottom: 2em;
    margin: auto;
    border-radius: 10px;
    background: white;
    width: fit-content;
    box-shadow: black 2px 2px 10px;
    display: grid;

    .pop-up-wrapper {
      display: inline-flex;
      color: black;


      &.error > div {
        border-color: red;
      }

        &.info > div {
          border-color: deepskyblue;
        }

        &.success > div {
          border-color: limegreen;
        }
      }



    .left {
      border-style: solid;
      margin-right: 20px;
      //border-color: deeppink;
      border-width: 10px;
      border-bottom-left-radius: 10px;
      border-top-left-radius: 10px;
    }

    .right {
      border-style: solid;
      margin-left: 20px;
      //border-color: deeppink;
      border-width: 10px;
      border-bottom-right-radius: 10px;
      border-top-right-radius: 10px;
    }
  }
  .pop-up-enter-active {
    transition: all 1s ease-out;
  }

  .pop-up-leave-active {
    transition: all 1s ease-out;
  }
  .pop-up-enter-from,
  .pop-up-leave-to {
    transform: translateY(2em);
    opacity: 0;
  }
  //@keyframes show-pop-up {
  //  0% {
  //    transform: translateY(0px);
  //  }
  //  100% {
  //    transform: translateY(300px);
  //  }
  //}
</style>