<template>
  <router-link :to="`/form/${id}/${suffix}`" class="goto-link form-link-wrapper">
    <img :src="`/img/${formTemplate}.png`" class="invert-image-color" alt="">
    <p>{{ formName }}</p>
  </router-link>
</template>

<script>
export default {
  name: "GotoFormDone",
  props: {
    id: {
      type: [String, Number],
      required: true,
    },
    suffix: {
      validator(value) {
        return ['fill', 'review', 'done'].includes(value)
      }
    },
    formTemplate: {
      type: String,
    },
    formName: {
      type: String,
    }
  }
}
</script>

<style scoped lang="less">

.goto-link {
  text-decoration: none;
}

.form-link-wrapper {
  text-align: center;
  background-color: transparent;
  border: var(--text-soft) 2px solid;
  border-radius: 10px;
  transition: all .2s ease-in-out;
  aspect-ratio: 1.5;
  padding: 0.125em 0.5em;
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover, &:focus-within, &:focus {
    background-color: rgba(255,255,255, 0.1);
    transform: scale(1.05);
  }
  img {
    width: 100px;
    margin-bottom: .8em;
  }
  p {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    text-decoration: none;
    font-size: 1.5rem;
    font-weight: bold;
    margin: 0;
  }
}

</style>