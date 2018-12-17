import Vue from "vue"
import axios from "axios"
import VueAxios from "vue-axios"
import API_URL from "@/common/config"

const Api = {
    init() {
        Vue.use(VueAxios, axios)
        Vue.axios.defaults.baseURL = API_URL
    },
    getSummary() {
        return Vue.axios.get("/games/summary")
          .then(response => (response.data))
          .catch(e => {throw new Error(e.response.data.message)})
    },
    getGames(queryParams) {
        return Vue.axios.get("/games", {params: queryParams})
          .then(response => (response.data))
          .catch(e => {throw new Error(e.response.data.message)})
    },
}

export default Api