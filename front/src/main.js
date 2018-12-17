import Vue from 'vue'
import VueResource from 'vue-resource'
import App from './App.vue'
import router from './router'

Vue.use(VueResource)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})