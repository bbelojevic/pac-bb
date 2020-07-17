import Vue from "vue";
import App from "./App.vue";
import router from "./routes";
import store from "./store";
import VueKeyCloak from "@dsb-norge/vue-keycloak-js";

Vue.config.productionTip = false

// Allow vue-devtools inspection - TODO -> Set false for production
Vue.config.devtools = true;

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
//     if (Vue.prototype.$keycloak.authenticated) {
//       tokenInterceptor();
//     }

    new Vue({
      router,
      store,
      render: (h) => h(App),
    }).$mount("#app");
  },
});