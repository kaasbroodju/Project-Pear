<template>
  <p class="label" :data-pear-required="validator !== null">{{ label }}</p>
  <data-point-type-radio-input :name="name" :data-point-types="pickableOptions" @change="changeExtraSkill" :validator="validator"></data-point-type-radio-input>
  <criteria-student v-if="extraSkill != null" :label="extraSkill" :name="name" :validator="extraSkill !== null ? {} : validator"></criteria-student>
</template>

<script>
import DataPointTypeRadioInput from "./DataPointTypeRadioInput.vue";
import criteriaStudent from "./CriteriaStudent.vue";
import fetchAuth from "@/utils/authrequest";
import {selfPickableStore} from "@/modules/formelements/store";
import {mapActions, mapState, mapStores} from "pinia/dist/pinia";

export default {
  name: "SelfPickableDataPoint",
  components: {DataPointTypeRadioInput, criteriaStudent},
  props: {
    label: "",
    name: String,
    validator: Object,
  },
  data() {
    return {
      extraSkill: null,
      options: [],
    }
  },
  computed: {
    pickableOptions() {
      const output = this.options.filter(option => !(this.dataPointTypesAlreadyInUse.includes(option) && option !== this.extraSkill))
      if (this.validator) {
        if (!this.validator.bannedTypes) return output;
        const bannedList = this.validator.bannedTypes.map(banned => banned.type)
        return output.filter(option => !bannedList.includes(option))
      } else {
        return output;
      }
    },
    ...mapStores(selfPickableStore),
    // gives read access to this.count and this.double
    ...mapState(selfPickableStore, ['dataPointTypesAlreadyInUse']),
  },
  mounted() {
    fetchAuth("/api/datapointtype")
        .then(response => response.json())
        .then(json => {
          this.options = json;
        })
  },
  methods: {
    changeExtraSkill(e) {
      this.remove(this.extraSkill)
      this.extraSkill = e;
      this.add(this.extraSkill)
    },
    ...mapActions(selfPickableStore, ['add', 'remove', 'clear']),
  },

}
</script>

<style scoped>

</style>