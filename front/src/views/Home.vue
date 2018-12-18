<template>
  <div class="home">
    <div class="title">Avocado compatibility list</div>
    <div class="desc">Links: 
      <a href="https://github.com/JaCzekanski/Avocado">Avocado</a>
      <a href="https://github.com/JaCzekanski/avocado-db">avocado-db</a>
      <a href="https://discord.gg/ATEJApR">Discord</a>
    </div>

    <Summary/>
    <GameList/>
  </div>
</template>

<script>
import Summary from "@/components/summary/Summary";
import GameList from "@/components/gameList/GameList";

export default {
  name: "home",
  components: {
    Summary,
    GameList
  },
  data: () => ({
  })
};
</script>

<style lang="scss" scoped>
@media only screen and (min-width: 950px) {
  .home {
    width: 900px;
  }
}

@media only screen and (max-width: 950px) {
  .home {
    width: 100%;
  }
}

.title {
  font-size: 36pt;
}
.desc {
  margin-top: 8px;
}
.desc a {
  margin-left:8px;
  margin-right:8px;
}
</style>