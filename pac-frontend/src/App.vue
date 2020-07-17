<template>
  <div class="container-fluid">
    <TopBar :authenticated="authenticated" :username="username" />
    <router-view />
  </div>
</template>

<script>
//import PacFrontendApp from './components/PacFrontendApp.vue'

/*export default {
  name: 'App',
  components: {
    PacFrontendApp
  }
}*/

import Vue from "vue";
import TopBar from "./components/TopBar.vue";

export default {
  name: "App",
  components: {
    TopBar
  },
  data() {
    return {
      authenticated: false,
      username: ""
    };
  },
  mounted() {
    //try {
      if (Vue.prototype.$keycloak.token !== null) {
        this.authenticated = true;
        this.username = Vue.prototype.$keycloak.userName;
        console.log(Vue.prototype.$keycloak.tokenParsed.realm_access.roles[0]);
        console.log(Vue.prototype.$keycloak.tokenParsed.realm_access.roles);
        console.log(Vue.prototype.$keycloak.tokenParsed);
        console.log(Vue.prototype.$keycloak.tokenParsed.email);
      } else {
        console.log("Not logged");
      }
    //} catch (e) {
      //
    //}
  }
};
</script>

<style>
@import url(https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css)
</style>