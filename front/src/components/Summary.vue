<template>
  <div>
    <h3>Summary</h3>
    <div class="summary">
      <template v-for="(value, key) in summary">
        <div><ButtonStatus :status="key"/></div>
        <div>{{ value }}</div>
        <div class="small">({{percent(value)}}%)</div>
        <div class="chartBarBg" v-if="key != 'Total'">
          <div class="chartBar" :data-status="key" :style="{width:percent(value) + '%'}"></div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import ButtonStatus from "@/components/ButtonStatus";
import Api from "@/common/api";

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
.small {
  font-size: 0.75em;
}
.summary {
  display: grid;
  grid-template-columns: max-content max-content max-content auto;
  row-gap: 0.2em;
  column-gap: 0.4em;

  margin-bottom: 16px;
}

.summary > div {
  display: flex;
  align-self: center;
}

$chartCornerRadius: 8px;
$chartHeight: 10px;

.chartBarBg {
  height: $chartHeight;
  background: #edf8ff;
  border-radius: $chartCornerRadius;
}
.chartBar {
  height: $chartHeight;
  background: linear-gradient(to left, #a8dcff, #78c9ff);
  border-radius: $chartCornerRadius;
}
</style>
