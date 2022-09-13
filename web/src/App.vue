<template>
  <p-w-a-prompt />
  <router-view />
  <bottom-popup/>
  <link :href="`/css/themes/${this.theme}.less`" type="text/less">
</template>

<style lang="less">

@import "assets/css/default.less";
html {
  background-color: var(--background);

  font-family: Avenir, Helvetica, Arial, sans-serif;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: var(--text);
  //color: var(--background);


  ///*color: #F6F6F6;*/
  background-color: var(--background );
}

button {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: var(--text);
  background-color: var(--background);
  border: var(--text) solid 1px;
  font-size: 1.25em;
  border-radius: .25em;
  padding: .125em .5em;

  &:hover, &:focus {
    background-color: var(--background-soft);

  }
}

.invert-image-color {
  -webkit-filter: invert(100%); /* Safari/Chrome */
  filter: invert(70%);
}

a {
  color: var(--text);
}

.noscroll {
  overflow: hidden;
}

textarea {
  font-family: inherit;
}

@import "./assets/css/animations/normal-slide.css";
@import "./assets/css/animations/reverse-slide.css";
@import "./assets/css/animations/slide-up.css";
@import "./assets/css/animations/slide-down.css";

</style>
<script>
import BottomPopup from '@/components/BottomPopup.vue';
import PWAPrompt from "@/components/PWAPrompt.vue";


export default {
  components: {PWAPrompt, BottomPopup},
  beforeMount() {
    const style = document.createElement('link');
    style.href = `css/themes/${this.theme}.less`

    style.type = "text/less"
    style.rel = 'stylesheet'
    document.head.append(style);
  },
  data() {
    return {
      theme: 'default'
    }
  },
}
</script>