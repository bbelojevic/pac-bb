import Vue from "vue";
import App from "./App.vue";
import router from "./routes";
import store from "./store";
import VueKeyCloak from "@dsb-norge/vue-keycloak-js";
import axios from 'axios';
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false

// Allow vue-devtools inspection - TODO -> Set false for production
Vue.config.devtools = true;

function tokenInterceptor() {
  axios.interceptors.request.use((config) => {
    config.headers.Authorization = `Bearer ${Vue.prototype.$keycloak.token}`;
    //console.log("config.headers.Authorization: " + config.headers.Authorization);
    return config;
  }, (error) => {
    return Promise.reject(error);
  });
}

// Use Material Design for Vue.js
Vue.use(Vuetify);

Vue.use(VueKeyCloak, {
  config: {
    authRealm: process.env.VUE_APP_KEYCLOAK_REALM,
    authClientId: process.env.VUE_APP_KEYCLOAK_CLIENT_ID,
    authUrl: process.env.VUE_APP_KEYCLOAK_AUTH_URL,
    logoutRedirectUri: process.env.VUE_APP_KEYCLOAK_REDIRECT_URL,
  },
  init: {
    onLoad: "check-sso",
  },
  onReady: () => {
    if (Vue.prototype.$keycloak.authenticated) { // use store?
      tokenInterceptor();
    }

    new Vue({
      router,
      store,
      vuetify: new Vuetify(),
      render: (h) => h(App),
    }).$mount("#app");
  },
});