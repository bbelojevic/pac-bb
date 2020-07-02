import Vue from "vue";
import App from "./App.vue";
import router from "./routes";

Vue.config.productionTip = false

// Allow vue-devtools inspection - TODO -> Set false for production
Vue.config.devtools = true;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')