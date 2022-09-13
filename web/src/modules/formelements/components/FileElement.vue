<template>
      <table class="section">
        <thead hide>
        <tr>
          <td>
            filename
          </td>
          <td>
            download
          </td>
          <td>
            remove
          </td>
        </tr>
        </thead>
        <tbody>
        <tr class="no-hover">
          <td>
            <form enctype="multipart/form-data" id="temp-upload" @change="uploadFiles">
              <label>bestand: <input type="file" name="image" />
              </label>
            </form>
          </td>
          <td colspan="3"><button type="button" class="download-all-button" @click="downloadAll" :disabled="downloadableFiles.size === 0">download all</button></td>
        </tr>
          <tr v-for="downloadable in downloadableFiles">
            <td>
              {{ downloadable }}
            </td>
            <td>
              <button type="button" @click="deleteFile(downloadable)">X</button>
              <button type="button" @click="downloadFile(downloadable)">download</button>
            </td>
          </tr>
        </tbody>
      </table>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "FileElement",
  props: {
    label: "",
    name: "",
    validator: Object
  },
  data() {
    return {
      downloadableFiles: new Set()
    }
  },
  mounted() {
    fetchAuth(`/api/form/${this.$route.params.id}/file/${this.name}/names`).then(response => response.json()).then(files => {


      this.downloadableFiles = new Set(files);

    })
  },
  methods: {
    uploadFiles(e) {
      e.preventDefault();

      const imageName = e.target.files[0].name;
      const formData = new FormData()
      formData.append('image', e.target.files[0])

      fetchAuth(`/api/form/${this.$route.params.id}/file/${this.name}`, {method: "POST", body: formData}).then(() => e.target.value = "").then(() => {this.downloadableFiles = this.downloadableFiles.add(imageName)})
    },
    downloadFile(e) {

      fetchAuth(`/api/form/${this.$route.params.id}/file/${this.name}/${e}`)
          .then((res) => { return res.blob(); })
          .then((data) => {
            const a = document.createElement("a");
            a.href = window.URL.createObjectURL(data);
            a.download = e;
            a.click();
          });
    },
    deleteFile(e) {

      fetchAuth(`/api/form/${this.$route.params.id}/file/${this.name}/${e}`, {method: "DELETE"})
          .then(() => {
            this.downloadableFiles.delete(e);
          })
    },
    downloadAll() {
      fetchAuth(`/api/form/${this.$route.params.id}/file/${this.name}`)
          .then((res) => { return res.blob(); })
          .then((data) => {
            const a = document.createElement("a");
            a.href = window.URL.createObjectURL(data);
            a.download = this.label + ".zip";
            a.click();
          });
    }
  }
}
</script>

<style src="@/assets/css/table.less"/>

<style scoped lang="less">
.download-all-button {
  display: flex;
  float: right;
}

table {

  tr.no-hover {
    &:hover, &:focus {
      background-color: unset;
    }
  }

  tr > td > button {
    display: flex;
    float: right;
    margin: 0 1em;


    &:first-child {
      margin-left: 0;
    }
  }

}

.section {
  text-align: unset;
  background-color: unset;
}
</style>