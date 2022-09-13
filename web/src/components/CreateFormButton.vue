<template>
    <button type="button" @click="createForm" @contextmenu.prevent="() => false" class="create-form-wrapper" :aria-label="`create ${formInstance}`">

      <img :src="`/img/${formInstance}.png`" class="invert-image-color" alt="" >
      <p>{{ formInstance }}</p>

    </button>
</template>

<script>

import fetchAuth from "@/utils/authrequest";

export default {
  name: "CreateFormButton",
  props: {
    formInstance: {type:String, required:true},
  },
  methods: {
    createForm() {
      fetchAuth(`/api/form/create/${this.formInstance}`, {method: "post"})
          .then(response => response.json())
          .then((id) => {
            this.$router.push({path: `/form/${id}/fill`})
          })
    }
  }
}
</script>

<style scoped lang="less">
.create-form-wrapper {
  text-align: center;
  background-color: transparent;
  border: var(--text-soft) 2px solid;
  border-radius: 10px;
  transition: all .2s ease-in-out;
  aspect-ratio: 1.5;

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