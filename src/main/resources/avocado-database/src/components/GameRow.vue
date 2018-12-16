<template>
  <tr>
    <td class="region" :data-region="game.region">
      <RegionFlag :region="game.region"/>{{ game.region }}
    </td>
    <td class="name">{{ game.name }}</td>
    <td class="issue">
      <a v-if="game.issue != null" :href="issueUrl(game.issue)">#{{game.issue}}</a>
    </td>
    <td class="status">
      <ButtonStatus :status="game.status"/>
    </td>
  </tr>
</template>

<script>
import ButtonStatus from "@/components/ButtonStatus";
import RegionFlag from "@/components/RegionFlag";

export default {
  name: "GameRow",
  props: {
    game: { type: Object, required: true }
  },
  components: {
    ButtonStatus,
    RegionFlag
  },
  methods: {
    issueUrl(issueNum) {
      return "https://github.com/JaCzekanski/Avocado/issues/" + issueNum;
    }
  }
};
</script>


<style lang="scss" scoped>
table td {
  padding: 0.2em 0.5em 0.2em 0.5em;
  font-size: 0.8em;
  align-items: center;
}

table tr:nth-child(odd) {
  background: #f8fff8;
}

table tr:nth-child(even) {
  background: #eaffea;
}

table tr:hover {
  background: #ffffff;
}
</style>
