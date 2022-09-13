<template>
  <div class="badge-wrapper">
    <div class="badge-image-wrapper">
      <img :src="`/badges/${name}.webp`" alt=""/>
    </div>
    <div class="badge-text-wrapper">
      <p>{{ name }}</p>
    </div>

  </div>

</template>
<script>
export default {
  name: 'badge',
  props: {
    name: {type:String, required:true}
  }
}
</script>
<style scoped lang="less">

div {
  margin: 0;
  padding: 0;
}

.badge-wrapper {
  background-color: #4b2a27;
  display: inline-flex;
  padding: .5em;
  margin: .25em;
  border-radius: .5em;
  //align-content: center;
  //justify-content: center;
  //grid-template-columns: 20% 1fr;
  //width: 4em;
  //width: fit-content;

  .badge-image-wrapper {
    display: flex;

    img {
      height: 1.781em;
      margin-right: .5em;
    }

  }

  .badge-text-wrapper {

    p {
      margin: .5em 0;
      font-size: .75em;
      display: inline-block;
      color: white;
    }
  }
}

</style>