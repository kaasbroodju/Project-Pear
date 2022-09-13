<template>
  <p class="label" :data-pear-required="validator !== null">{{ this.label }}</p>
  <div class="fill-hboi">
    <div class="fill-hboi-selection">
      <label>
        <select :name="name" :data-betsoc-label="name" :required="validator !== null" @change="(e) => changeHBOIDescription(e)">
          <option disabled selected value> -- selecteer een optie -- </option>
          <optgroup
              v-for="[key, value] in this.competences"
              :label="key"
          >
            <option
                v-for="competence in value"
                v-bind:key="competence"
                :value="`${competence.mainCompetence}()${competence.subCompetence}()${competence.level}`"
                :description="competence.description"
            >
              {{ `${competence.mainCompetence} ${competence.subCompetence} ${competence.level}` }}
            </option>
          </optgroup>
        </select>
      </label>
    </div>
    <div class="fill-hboi-description">
      <div :name="name + 'Description'" ref="description"></div>
    </div>
  </div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "Competence",
  props: {
    label: String,
    name: String,
    validator: Object,
  },
  data() {
    return {
      competences: new Map()
    }
  },
  beforeMount() {
    this.fetchHBOICompetence();
  },
  methods: {
    fetchHBOICompetence() {
      return fetchAuth("/api/competence")
          .then(response => response.json())
          .then(json => {
            // const mapTemp = new Map();
            for (let competence of json) {
              //
              if (this.competences.has(competence.mainCompetence)) {
                this.competences.get(competence.mainCompetence).push(competence)
              } else {
                this.competences.set(competence.mainCompetence, [competence])
              }
            }
          })
    },
    changeHBOIDescription(option) {

      this.$refs.description.innerText = option.target.selectedOptions[0].__vnode.props.description;
    },
  }
}
</script>

<style scoped lang="less">
.fill-hboi {
  display: flex;
  min-height: 4rem;
  div {
    padding: 1em;
  }
  .fill-hboi-selection {
    width: 30%;
    select {
      width: 100%;
    }
  }
  .fill-hboi-description {
    width: 70%;
    min-height: 12em;
    > p {
      margin: 0;
      text-align: initial;
    }
  }
}
</style>