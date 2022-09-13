<template>
  <label>
    <input type="radio" :value="text" name="degreeOfAssessment" required>
    <img :src="src" class="invert-image-color" alt="">
    <p>{{ text.toLowerCase().replaceAll("_", " ")}}</p>
  </label>
</template>

<script>
export default {
  name: "ImageRadioInput",
  props: {
    src: "",
    text: ""
  }

}
</script>

<style scoped lang="less">
label {
  margin: 1em;
  font-size: 2em;
  transition: all .2s ease-in-out;

  &:hover, &:focus-within, &:focus {
    transform: scale(1.05);
  }
}
input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

img {
  height: 5em;
  display: inline;
}


div {
  display: inline-block;
}

p {
  margin: 0 0.25em;
}
</style>