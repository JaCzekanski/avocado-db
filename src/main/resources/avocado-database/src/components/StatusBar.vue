<template>
    <div class="statusBar">
      <ButtonStatus
        v-for="s in statuses"
        :key="s"
        :status="s"
        :clickable="true"
        v-bind:class="{ checked: s == status }"
        v-on:click="clicked"
      />
    </div>
</template>

<script>
import ButtonStatus from "@/components/ButtonStatus";

export default {
  name: "StatusBar",
  data: () => ({
    statuses: ["Playable", "InGame", "IntroMenus", "Broken", "Unknown"],
    status: null,
  }),
  components: {
    ButtonStatus
  },
  methods: {
      clicked(status) {
        this.status = (status == this.status) ? null : status;
        this.$emit('clicked', this.status)
      }
  }
};
</script>

<style lang="scss" scoped>
.statusBar {
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-end;
}
</style>
