<template>
  <h3>{{ label }}</h3>
  <div class="skill-radio-wrapper">
    <template v-for="key in keys" v-bind:key="key">
      <input type="radio" :id="`skill-radio-${name}-${key}`" :name="name" :data-betsoc-label="name" :value="key" :disabled="disabled">
      <label :for="`skill-radio-${name}-${key}`">
        <img src="@/assets/temp_dev_image.svg">
        {{ this.skills[key].label }}
      </label>
    </template>

    <template v-if="ableToNone || false">
      <input type="radio" :id="`skill-radio-${name}-none`" :name="name" :data-betsoc-label="name" value="undefined" :disabled="disabled">
      <label :for="`skill-radio-${name}-none`">
        <img src="@/assets/temp_dev_image.svg">
        {{ 'none' }}
      </label>
    </template>
  </div>
</template>

<script>
import {Skill} from "@/utils/constants";

export default {
  name: "SkillRadio",
  props: {
    label: String,
    name: String,
    disabled: Boolean,
    ableToNone: Boolean,
    unPickAbleSkills: Array,

  },
  data() {
    return {
      skills: Skill
    }
  },
  computed: {
    keys() {
      if (this.unPickAbleSkills) {
        return Object.keys(Skill).filter(skill => !this.unPickAbleSkills.includes(skill));
      } else {
        return Object.keys(Skill);
      }
    }
  }
}
</script>

<style scoped lang="less">
.skill-radio-wrapper {
  //display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  //margin: 0px 2em;
  overflow-x: hidden;

  img {
    width: 2em;
    height: 2em;
  }
  label {
    margin: .25em;
    font-weight: bold;
    font-size: 1em;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  input {
    position: absolute;
    width: 0;
    opacity: 0;
    pointer-events: none;
  }
  input:checked + label, input:focus + label {
    transform: scale(1.25);
    font-weight: bolder;
  }
  input:checked + label {
    text-decoration: underline;
  }
}

</style>