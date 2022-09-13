<template>
  <div>
    <label>
      Datum start:
      <input type="date" :name="name + 'Date'" :data-betsoc-label="name + 'Date'">
    </label>
    <status-radio-input :name="name"></status-radio-input>
    <br>
    <br>
    <text-box label="actie" :name="name + 'Action'" />
    <br>
    <text-box label="studentreflectie" :name="name + 'StudentReflection'" />
    <br>
    <text-box label="docent feedback" :name="name + 'TeacherFeedback'"></text-box>
    <br>
    <hr>
  </div>
</template>

<script>
import TextBox from "@/modules/formelements/components/TextBox.vue";
import StatusRadioInput from "@/modules/formelements/components/StatusRadioInput.vue";

export default {
  name: "Action",
  components: {StatusRadioInput, TextBox},
  props: {
    name: String
  }
}
</script>

<style scoped>

</style>