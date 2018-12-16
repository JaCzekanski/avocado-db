<template>
  <div class="home">
    <h1>Avocado compability list</h1>

    <Summary/>

    <StatusBar v-on:clicked="status = $event"/>
    <input class="search" v-model="phrase" placeholder="Search ...">
    <button v-on:click="clearAll">Reset all</button>
    <Alphabet v-on:selected="letter = $event"/>

    <table class="table">
      <tr>
        <th
          v-for="col in columns"
          v-bind:class="col"
          :key="col"
          :data-column="col"
          @click="sort"
        >
          {{col}}<span :data-active="sortBy == col" :data-dir="sortDir" class="sortArrow"></span>
        </th>
      </tr>
      <GameRow v-for="game in gameList" :key="game.name" :game="game"/>
    </table>
    <div class="fill" v-if="loading">
      <div><Spinner/></div>
    </div>
    <div class="fill" v-else-if="error">
      <div class="error">{{ error }}</div>
    </div>
    <div class="fill" v-else-if="!gameList.length">
      <div>No results</div>
    </div>
  </div>
</template>

<script>
import Alphabet from "@/components/Alphabet";
import ButtonStatus from "@/components/ButtonStatus";
import GameRow from "@/components/GameRow";
import Spinner from "@/components/Spinner";
import Summary from "@/components/Summary";
import StatusBar from "@/components/StatusBar";
import Api from "@/common/api";

export default {
  name: "home",
  components: {
    Alphabet,
    ButtonStatus,
    GameRow,
    Spinner,
    Summary,
    StatusBar
  },
  data: () => ({
    columns: ["region", "name", "issue", "status"],
    loading: false,
    error: null,
    sortDir: "",
    sortBy: "name",
    phrase: null,
    status: null,
    letter: null,
    summary: [],
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
    fetchGames: function() {
      this.error = null;
      this.loading = true;
      Api.getGames({
        sort: this.sortDir + this.sortBy,
        letter: this.letter,
        phrase: this.phrase,
        status: this.status
      })
        .then(response => (this.gameList = response))
        .catch(e => (this.error = e.message))
        .then(() => (this.loading = false));
    },
    sort: function(e) {
      console.log(e)
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
    }
  }
};
</script>

<style>
.table {
  border: 1px #ebebeb solid;
  border-spacing: 0;
}

@media only screen and (min-width: 1200px) {
  .table {
    width: 1000px;
  }
}

@media only screen and (max-width: 1200px) {
  .table {
    width: 100%;
  }

  .issue {
    display: none;
  }
}

.search {
  width: 85%;
  margin-bottom: 16px;
  margin-top: 16px;

  color: inherit;
  font: inherit;
  margin-top: 1em;
  margin-bottom: 1em;
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