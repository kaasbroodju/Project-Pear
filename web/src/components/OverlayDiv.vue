<template>
  <div v-if="show" class="div-overlay" @click="() => {if (!required) hideOverlay();}">
    <div v-if="!customWrapper" @click="(e) => {e.stopPropagation()}" class="overlay-inside-div-wrapper">
      <slot>

      </slot>
    </div>
    <slot v-else @click="(e) => {e.stopPropagation()}">

    </slot>
  </div>
</template>

<script>
export default {
  name: "OverlayDiv",
  props: {
    required: {
      type: Boolean,
      required: false,
      default: false,
    },
    customWrapper: {
      type: Boolean,
      required: false,
      default: false,
    }
  },
  data() {
    return {
      show: false
    }
  },
  methods: {
    showOverlay() {
      this.show = true;
      document.querySelector("body").classList.add("noscroll")
    },
    hideOverlay() {
      this.show = false;
      document.querySelector("body").classList.remove("noscroll")

    }
  }
}
</script>

<style scoped lang="less">
.div-overlay {
  //position: fixed;
  overflow-y: scroll;
  top: 0; right: 0; bottom: 0; left: 0;
  background-color: rgba(0,0,0,0.75);
  //height: calc(100vh + 82px);
  display: grid;
  justify-content: center;
  align-items: center;
  position: fixed;
  height: 100vh;
  width: 100vw;
  z-index: 1000;

  &> div {
    //margin-top: 10vh;
    width: 80vw;

    height: calc(100% - 4em);
    //min-height: 10em;
    //min-width: 10em;
    //max-height: 90vh;
    //max-width: 90vw;
    background-color: var(--background);
    border-radius: 1em;
    overflow-y: scroll;
  }
}
</style>

<style>
.overlay-inside-div-wrapper > .done-form-wrapper {
  width: 100%;
}
</style>