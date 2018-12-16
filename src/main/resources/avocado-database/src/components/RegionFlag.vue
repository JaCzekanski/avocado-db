<template>
  <span class="region" :data-region="region" />
</template>

<script>
export default {
  name: "RegionFlag",
  props: {
    region: { type: String, required: true },
  }
};
</script>


<style lang="scss" scoped>
.region::before {
  content: "🏳";
  margin-right: 6px;

}
.region[data-region="NTSC"]::before {
  content: "🇺🇸";
}

.region[data-region="NTSCJ"]::before {
  content: "🇯🇵";
}

.region[data-region="PAL"]::before {
  content: "🇪🇺";
}
</style>
