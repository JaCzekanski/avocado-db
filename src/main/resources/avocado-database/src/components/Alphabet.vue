<template>
  <div class="alphabet">
    <div
      v-for="i in alphabet"
      :data-letter="i"
      v-bind:class="{ checkedLetter: i == letter }"
      v-on:click="letterSelected"
    >{{ i }}</div>
  </div>
</template>

<script>
export default {
  name: "Alphabet",
  data: () => ({
    alphabet: [],
    letter: null
  }),
  mounted() {
    this.alphabet = this.generateAlphabet();
  },
  methods: {
    generateAlphabet() {
      let a = [];

      a.push("0-9")
      for (var i = 0; i < 26; i++) {
        a.push(String.fromCharCode(65 + i));
      }

      return a;
    },
    letterSelected(e) {
      const clicked = e.currentTarget.dataset["letter"];
      if (clicked == this.letter) {
        this.letter = null;
      } else {
        this.letter = clicked;
      }

      let selected = this.letter
      if (selected == "0-9") selected = "0"
      this.$emit("selected", selected)
    }
  }
};
</script>

<style lang="scss" scoped>
.alphabet {
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  font-size: 0.8em;
}

.alphabet div {
  flex-grow: 1;
  text-align: center;
  vertical-align: middle;

  font-size: 1.25em;
  font-weight: bold;

  padding: 0.5em 0 0.5em 0;

  cursor: pointer;
  color: #44f;
}

.alphabet div:hover {
  background: #eee;
}

.alphabet .checkedLetter {
  color: #f44;
}
</style>
