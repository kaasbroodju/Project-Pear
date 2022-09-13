<template>
  <favourite-button :favourite="favourite" :email="email" :name="name"/>
  <button @click="this.$emit('selectedReviewer',{email, name})">{{name}}|    |{{email}}|    |{{subTitle}}</button>
</template>

<script>
import FavouriteButton from "@/components/FavouriteButton.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "PossibleReviewer",
  components: {FavouriteButton},
  data() {
    return {
      title: "unfavourite",
    }
  },
  props: {
    name: String,
    email: String,
    subTitle: String,
    favourite: Boolean
  },
  methods: {
    changeLabel() {
      if (this.$refs.checkbox.checked) {
        fetchAuth("/api/favourite/" + this.email, {method: "post"}).then(response => response.ok).then(ok => {
          if (ok) this.$emit("addFavourite", {'email': this.email, 'name': this.name});
        })
      } else {
        fetchAuth("/api/favourite/" + this.email, {method: "delete"}).then(response => response.ok).then(ok => {
          if (ok) this.$emit("removeFavourite", {'email': this.email});
        })
      }
    }
  },
  emits: {
    'addFavourite': ({email, name}) => {
      return !!(email && name);
    },
    'removeFavourite': ({email}) => {
      return !!(email);
    },
    'selectedReviewer': ({email, name}) => {
      return !!(email && name);
    }
  },
}
</script>

<style scoped>
input:checked + svg path {
  fill: yellow;
}

input:not(:checked) + svg path {
  fill: darkgray;
}
svg  {
  height: 1.5em;
}

input[type="checkbox"] {
  display: none;
}

button {
  border: none;
  background: inherit;
}
</style>