<template>
  <tr>
    <td class="region" :data-region="game.region">
      <RegionFlag :region="game.region"/>{{ game.region }}
    </td>
    <td class="name"><a :href="redumpUrl(game)">{{ game.name }}</a></td>
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
    redumpUrl(game) {
      let region = ""
      if (game.region != "UNKNOWN") {
        region = "/region/" 
        if (game.region == "PAL") region += "Eu"
        else if (game.region == "NTSC") region += "Am"
        else if (game.region == "NTSCJ") region += "As"
      }
      let quicksearch = "/quicksearch/" +game.name.split(" ").join("-")
      return "http://redump.org/discs/system/psx" + quicksearch + region
    },
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
