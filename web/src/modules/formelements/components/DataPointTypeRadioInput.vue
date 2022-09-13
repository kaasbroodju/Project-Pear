<template>
  <div>
    <p class="label">{{ label }}</p>
    <label
        v-for="key in this.pickableOptions"
        v-bind:key="key"
    >
      <input
          type="radio"
          :name="name + 'DataPointType'"
          :data-betsoc-label="name + 'DataPointType'"
          :required="validator !== null"
          :value="key"
          @change="this.$emit('change', key)"
          :onresize="() => onfetch(key)"
      >{{ key }}</label>
    <label v-if="validator === null">
      <input
          type="radio"
          :name="name + 'DataPointType'"
          :data-betsoc-label="name + 'DataPointType'"
          value="undefined" @change="this.$emit('change', null)"
          :onresize="() => onfetch(null)"
      >none</label>
  </div>
</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "DataPointTypeRadioInput",
  props: {
    label: "",
    name: String,
    dataPointTypes: {
      type:Array,
      required: false,
      default: []
    },
    validator: Object
  },
  data() {
    return {
      fetched: false,
      options: []
    }
  },
  computed: {
    pickableOptions() {
      return this.dataPointTypes.length === 0 ? this.options : this.dataPointTypes;
    }
  },
  mounted() {
    // todo change to fetch
    this.options = ["Juiste kennis ontwikkelen","Kwalatief product maken","Creatief werken","Kritisch oordelen","Samenwerken","Boodschap overbrengen","Plannen","Aanpassingsvermogen","Proactief handelen","Reflecteren"];
    // if (this.dataPointTypes.length === 0) {
    //   fetchAuth("/api/datapointtype")
    //       .then(response => response.json())
    //       .then(json => {
    //         this.options = json;
    //       })
    // }
  },
  methods: {
    onfetch(e) {
      if (!this.fetched) {
        this.fetched = true;
        this.$emit('change', e)
      }
    }
  },
  emits: ['change']
}
</script>

<style scoped>

</style>