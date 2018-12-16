<template>
  <div>
    <h3>Summary</h3>
    <div v-for="(value, key) in summary">
      <ButtonStatus :status="key" class="row"/>
      {{ value }} ({{percent(value)}}%)
    </div>
  </div>
</template>

<script>
import ButtonStatus from "@/components/ButtonStatus";
import Api from "@/common/api"

export default {
  name: "Summary",
  data: () => ({
    summary: {}
  }),
  components: {
    ButtonStatus
  },
  mounted() {
    this.summary = this.fetchSummary();
  },
  methods: {
    fetchSummary() {
      Api.getSummary()
        .then(response => (this.summary = response))
        .catch(e => console.log(e));
    },
    percent(value) {
      return parseFloat((value / this.summary["Total"]) * 100).toFixed(2);
    }
  }
};
</script>

<style lang="scss" scoped>
.row {
  margin-bottom: 4px;
}
</style>
