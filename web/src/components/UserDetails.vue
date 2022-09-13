<template>
  <div>
    <div class="profile-picture-wrapper">
      <img ref="profileImage" src="@/assets/temp_dev_user.png" class="profile-picture" alt=""/>
    </div>
    <p ref="userName">User Name</p>
    <div class="badges">
      <badge
          v-for="badge in badges"
          v-bind:key="badge"
          :name="badge"
      />
    </div>

  </div>
</template>

<script>
import Badge from "@/components/Badge.vue";
import fetchAuth from "@/utils/authrequest";

export default {
  name: "UserDetails",
  components: {Badge},
  data() {
    return {
      badges: [],
      name: 'User Name'
    }
  },
  beforeMount() {
    fetchAuth("/api/badge", {cache: "force-cache"}).then(response => response.json()).then(badges => this.badges = badges.sort());
    fetchAuth("/api/settings/user/picture").then(response => {
      if (response.status === 204) {
        throw new Error('No profile picture');
      }
      return response.blob();
    }).then((blob) => {
      this.$refs.profileImage.classList.remove("invert-image-color")
      this.$refs.profileImage.src = window.URL.createObjectURL(blob)
    }).catch(() => {
      this.$refs.profileImage.classList.add("invert-image-color")
    })
    fetchAuth("/api/user/name").then(response => response.text()).then(name => this.$refs.userName.textContent = name)
  }
}
</script>

<style scoped>
.profile-picture-wrapper {
  margin: 1em 25%;
  border-radius: 50%;
  display: grid;
  align-content: center;
  justify-content: center;
}
.profile-picture {
  width: 100%;
  border-radius: 50%;
  object-fit: cover;
  aspect-ratio: 1;
}

p {
  text-align: center;
  font-size: 2em;
  font-weight: bold;
}

.badges {
  margin: 0 .5em;
}

</style>