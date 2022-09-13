<template>
  <div style="width: 100vw">
    <h1 class="form-title">{{ this.$route.params.formtemplate }}</h1>
    <table>
      <thead>
      <tr>
        <td>student</td>
        <td>form name</td>
        <td>date</td>
      </tr>
      </thead>
      <tbody>
      <template v-for="(value, key) in layoutRefactor">
        <tr v-for="student in value.students">
          <td>{{ student.studentName }}</td>
          <td>{{ student.formName }}</td>
          <td>{{ new Date(student.finishedAt).toLocaleString([], {year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute:'2-digit'}) }}</td>
        </tr>
      </template>

      </tbody>
    </table>
    <div v-for="(value, versionId) in this.layoutRefactor" :key="versionId">
      <template v-for="templateElement in value.template">
        <table v-if="templateElement.type !== 'SectionElement'">
          <caption>{{ templateElement.label }}</caption>
          <tbody>
          <tr v-for="student in value.students">
            <td>{{ student.studentName }}</td>
            <td :data-betsoc-label="student.studentName + templateElement.name"></td>
          </tr>
          </tbody>
        </table>
        <h2 v-else class="section">{{ templateElement.label }}</h2>
      </template>

    </div>
  </div>

</template>

<script>
import fetchAuth from "@/utils/authrequest";
import {doneElements} from "@/modules/formelements/formElementsIndex";
import {markdown} from "@/utils/markdown";

export default {
  name: "FormSummary",
  components: doneElements,
  data() {
    return {
      formMetas: {},
      layout: [],
      layoutRefactor: []
    }
  },
  mounted() {
    fetchAuth(`/api/teacher/formtemplate/${this.$route.params.groupname}/${this.$route.params.formtemplate}`)
        .then(response => response.json())
        .then(async (result) => {
          const versions = {}
          for (let key in result) {
            if (versions[result[key].formTemplate.version]) {
              versions[result[key].formTemplate.version].students.push({studentName: key, formName:result[key].name, id:result[key].id, finishedAt:result[key].finishedAt})
            } else {
              versions[result[key].formTemplate.version] = {}
              versions[result[key].formTemplate.version].students = [{studentName: key, formName:result[key].name, id:result[key].id, finishedAt:result[key].finishedAt}];
              await fetchAuth(`/api/formtemplate/${result[key].formTemplate.templateName}/${result[key].formTemplate.version}`, {cache: "force-cache"})
                  .then(response => response.json())
                  .then(layout => versions[result[key].formTemplate.version].template = layout)
            }
          }
          this.layoutRefactor = versions

          for (let key in result) {
            fetchAuth(`/api/form/${result[key].id}`)
                .then(response => response.json())
                .then(async (jsonData) => {
                  for (let name of Object.keys(jsonData)) {
                    const queryName = key + name;


                    let inputField = document.querySelector(`[data-betsoc-label="${queryName}"]`)
                    if (inputField == null || jsonData[name] == null) continue;
                    if (inputField.type === "radio") {
                      inputField = document.querySelector(`input[data-betsoc-label="${queryName}"][value="${jsonData[name]}"]`)
                      inputField.checked = true;
                    } else {
                      inputField.value = jsonData[name]
                      if (inputField instanceof HTMLDivElement) {
                        inputField.innerHTML = markdown.render(jsonData[name]);
                      } else if (inputField instanceof HTMLParagraphElement) {
                        inputField.innerText = jsonData[name]
                      } else if (inputField instanceof HTMLTableCellElement) {
                        inputField.innerText = jsonData[name]
                      }
                    }
                    if (inputField.onresize) await this.$nextTick(() => inputField.onresize.call(this))
                  }
                })
          }


        })
  }
}
</script>

<style src="@/assets/css/table.less" />

<style scoped lang="less">
caption {
  font-size: 1.25em;
}
table {
  margin-bottom: 1em;

  tbody {
    tr {
      td:first-child {
        width: 20%;
      }
    }
  }
}
</style>