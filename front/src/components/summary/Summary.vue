<template>
  <div>
    <h3>Summary</h3>
    <div class="frame">
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
      <div class="overlay" v-if="loading">
          <Spinner/>
      </div>
    </div>
  </div>
</template>

<script>
import ButtonStatus from "@/components/common/ButtonStatus";
import Spinner from "@/components/common/Spinner";

export default {
  name: "Summary",
  data: () => ({
    loading: false,
    summary: {
      "Playable": 0,
      "InGame": 0,
      "IntroMenus": 0,
      "Broken": 0,
      "Unknown": 0,
      "Total": 0
    }
  }),
  components: {
    ButtonStatus,
    Spinner
  },
  mounted() {
    this.fetchSummary();
  },
  methods: {
    async fetchSummary() {
      this.loading = true
      
      try {
        const {body: data} = await this.$http.get("/api/games/summary")

        this.summary = data
      } finally {
        this.loading = false
      }
    },
    percent(value) {
      if (!this.summary["Total"]) return "0"
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

.frame {
  position:relative;
}

.overlay {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0; top: 0; right: 0; bottom: 0;

  background: #ffffffa0;
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
}
</style>
