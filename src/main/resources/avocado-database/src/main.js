import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Api from './common/api'

Vue.config.productionTip = false

Api.init()

new Vue({
  router,
  render: function (h) { return h(App) }
}).$mount('#app')
