<template>
  <tr>
    <td class="region" :data-region="game.region">
      <RegionFlag :region="game.region"/>{{ game.region }}
    </td>
    <td class="name"><a :href="redumpUrl(game)">{{ game.name }}</a></td>
    <td class="updated">
      {{ formatDate(game.updated) }}
    </td>
    <td class="issue">
      <a v-if="game.issue != null" :href="issueUrl(game.issue)">#{{game.issue}}</a>
    </td>
    <td class="build">
      <a v-if="game.build" :href="commitUrl(game.build)">{{game.build}}</a>
    </td>
    <td class="status">
      <ButtonStatus :status="game.status"/>
    </td>
  </tr>
</template>

<script>
import Moment from "moment";
import ButtonStatus from "@/components/common/ButtonStatus";
import RegionFlag from "./RegionFlag";

const PROJECT_URL = "https://github.com/JaCzekanski/Avocado"

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
      return `${PROJECT_URL}/issues/${issueNum}`
    },
    commitUrl(commit) {
      return `${PROJECT_URL}/commit/${commit}`;
    },
    formatDate(date) {
      if (date == null) return ""
      return Moment(date).format("YYYY/MM/DD")
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
