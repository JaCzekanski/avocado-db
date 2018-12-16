<template>
  <div
    class="buttonStatus"
    :data-status="status"
    v-bind:class="{ clickable: clickable }"
    v-on:click="clicked"
  >{{ status }}</div>
</template>

<script>
export default {
  name: "ButtonStatus",
  props: {
    status: { type: String, required: true },
    clickable: { type: Boolean, required: false }
  },
  methods: {
    clicked() {
      if (this.clickable) {
        this.$emit("click", this.status)
      }
    }
  }
};
</script>


<style lang="scss" scoped>
.buttonStatus {
  display: inline-block;

  margin: 0 4px 0 4px;
  border-radius: 4px;
  color: white;
  padding: 0.2em 0.2em;
  width: 80px;
  text-align: center;
  font-size: 10pt;
  font-family: Arial, Helvetica, sans-serif;
  border: 2px solid transparent;
}

.buttonStatus.clickable {
  cursor: pointer;
}

.buttonStatus.checked {
  border: 2px solid red;
}

.buttonStatus[data-status="Total"] {
  color: black;
}

.buttonStatus[data-status="Playable"] {
  background: #31be6c;
}

.buttonStatus[data-status="InGame"] {
  background: rgb(255, 242, 64);
  color: black;
}

.buttonStatus[data-status="IntroMenus"] {
  background: rgb(255, 143, 24);
}

.buttonStatus[data-status="Broken"] {
  background: #e6594d;
}

.buttonStatus[data-status="Unknown"] {
  background: #000000;
}
</style>
