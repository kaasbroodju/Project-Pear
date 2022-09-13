<template>
  <div style="width: 100vw;">
    <label>
      mijn gilde
      <select name="test" @change="changeGuild">
        <option :selected="'' === myGuild" disabled>-- geen gilde --</option>
        <option v-for="guild in guilds" :selected="guild === myGuild" :value="guild" @change="changeGuild(guild)" >{{ guild }}</option>
      </select>
    </label>

    <form enctype="multipart/form-data" id="temp-upload" @change="uploadFiles">
      <div class="files">
        <div class="file-wrapper">
          <label>bestand: <input type="file" name="image" accept="image/png" />
          </label>
        </div>
      </div>
    </form>

    <div class="profile-picture-example-wrapper">
      <div class="profile-picture-example">
        <img ref="profileImage" src="@/assets/temp_dev_user.png" />
      </div>

    </div>
    <div>
      <user-badges></user-badges>
    </div>
  </div>

</template>

<script>
import fetchAuth from "@/utils/authrequest";
import UserBadges from "@/components/UserBadges.vue";

export default {
  name: "Settings",
  components: {UserBadges},
  data() {
    return {
      myGuild: '',
      guilds: [],
    }
  },
  mounted() {
    this.setUp();
    this.fetchPFP();

  },
  methods: {
    async setUp() {
      const myguild = fetchAuth("/api/guild/name").then(response => response.text())
      const guilds = fetchAuth("/api/guild/name/all").then(response => response.json())

      const result = await Promise.all([myguild, guilds])

      this.myGuild = result[0];
      this.guilds = result[1];
    },
    changeGuild(e) {
      fetchAuth("/api/settings/guild/" + e.target.value, {method:"PATCH"})
    },
    uploadFiles(e) {
      e.preventDefault();

      this.imageName = e.target.files[0].name;
      const formData = new FormData()
      formData.append('image', e.target.files[0])

      fetchAuth("/api/settings/user/picture", {method: "POST", body: formData}).then(this.fetchPFP)
    },
    fetchPFP() {
      fetchAuth("/api/settings/user/picture", {cache: "reload"}).then(response => response.blob()).then((blob) => {
        this.$refs.profileImage.src = window.URL.createObjectURL(blob)
      })
    }
  }
}
</script>

<style scoped lang="less">
.profile-picture-example-wrapper {
  width: 30vw;
}
.profile-picture-example {
  margin: 1em 25%;
  img {
    width: 100%;
    border-radius: 50%;
    object-fit: cover;
    aspect-ratio: 1;
  }

}

</style>