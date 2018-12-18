<template>
  <div>
    <StatusBar v-on:clicked="status = $event"/>

    <div class="searchBar">
      <div>
        <input class="search" v-model="phrase" placeholder="Search ...">
      </div>
      <div>
        <button v-on:click="clearAll">Reset all</button>
      </div>
    </div>

    <Alphabet :letter="letter" v-on:click="letter = $event"/>

    <table class="gameTable">
      <tr>
        <th v-for="col in columns" v-bind:class="col" :key="col" :data-column="col" @click="sort">
          {{col}}<span :data-active="sortBy == col" :data-dir="sortDir" class="sortArrow"></span>
        </th>
      </tr>
      <GameRow v-for="game in gameList" :key="game.name" :game="game"/>
    </table>

    <div class="fill">
      <div v-if="loading">
        <Spinner/>
      </div>
      <div v-else-if="error" class="error">{{ error }}</div>
      <div v-else-if="!gameList.length">No results</div>
    </div>
  </div>
</template>

<script>
import Alphabet from "./Alphabet";
import GameRow from "./GameRow";
import Spinner from "@/components/common/Spinner";
import StatusBar from "./StatusBar";

export default {
  name: "GameList",
  components: {
    Alphabet,
    GameRow,
    Spinner,
    StatusBar
  },
  data: () => ({
    columns: ["region", "name", "updated", "issue", "build", "status"],
    loading: false,
    error: null,
    sortDir: "",
    sortBy: "name",
    phrase: null,
    status: null,
    letter: "",
    gameList: []
  }),

  watch: {
    phrase: "fetchGames",
    letter: "fetchGames",
    status: "fetchGames"
  },

  created: function() {
    this.fetchGames();
  },

  methods: {
    async fetchGames() {
      this.error = null;
      this.loading = true;
      try {
        const { body: data } = await this.$http.get("/api/games", {
          params: {
            sort: this.sortDir + this.sortBy,
            letter: this.mapLetter(this.letter),
            phrase: this.phrase,
            status: this.status
          }
        });

        this.gameList = data;
      } catch (ex) {
        this.gameList = [];
        this.error = ex.body.message;
      } finally {
        this.loading = false;
      }
    },
    sort: function(e) {
      const clicked = e.currentTarget.dataset["column"];
      if (clicked != this.sortBy) {
        this.sortDir = "";
      } else {
        this.sortDir = this.sortDir == "" ? "-" : "";
      }
      this.sortBy = clicked;

      this.fetchGames();
    },
    clearAll: function() {
      this.sortDir = "";
      this.sortBy = "name";
      this.phrase = null;
      this.status = null;
      this.letter = null;
      this.fetchGames();
    },
    mapLetter(letter) {
      if (letter == "0-9") return "0";
      return letter;
    }
  }
};
</script>

<style lang="scss">
.gameTable {
  border: 1px #ebebeb solid;
  border-spacing: 0;
}

@media only screen and (min-width: 950px) {
  .gameTable {
    width: 900px;
  }
}

@media only screen and (max-width: 950px) {
  .gameTable {
    width: 100%;
  }
  .updated {
    display: none;
  }
  .issue {
    display: none;
  }
  .build {
    display: none;
  }
}

.searchBar {
  display: grid;
  grid-template-columns: auto max-content;
  column-gap: 2em;
  margin-top: 1em;
  margin-bottom: 1em;
}
.searchBar * {
  color: inherit;
  font: inherit;
  width: 100%;
}

table th {
  text-align: left;
  padding: 0.2em 0.4em 0.2em 0.4em;

  font-size: 1.25em;
  font-weight: bold;

  cursor: pointer;
  color: #44f;

  user-select: none;
}

table th:hover {
  background: #eeeeee;
}

table th:first-letter {
  text-transform: capitalize;
}

.fill {
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  padding: 0.5em;
}

.fill div {
  flex-grow: 1;
  font-size: 1.5em;
  text-align: center;
  background: white;
}

.fill .error {
  color: red;
}

.region {
}

.name {
  width: 100%;
}

.status {
}

.issue {
}

.sortArrow[data-dir=""]::before {
  content: "🔺";
  font-size: 12px;
  margin-left: 6px;
}

.sortArrow[data-dir="-"]::before {
  content: "🔻";
  font-size: 12px;
  margin-left: 6px;
}

.sortArrow {
  visibility: hidden;
}

.sortArrow[data-active] {
  visibility: visible;
}
</style>