<template>
  <div class="container-fluid">
    <TopBar />
    <router-view />
  </div>
</template>

<script>
import Vue from "vue";
import TopBar from "./components/TopBar.vue";

export default {
  name: "App",
  components: {
    TopBar
  },
  mounted() {
    console.log(Vue.prototype.$keycloak.token);
    console.log(Vue.prototype.$keycloak.authenticated);
    //try {
      if (Vue.prototype.$keycloak.token !== null) {
        this.$store.dispatch('setAuthenticated', Vue.prototype.$keycloak.authenticated);
        this.$store.dispatch('setToken', Vue.prototype.$keycloak.token);
        this.$store.dispatch('setEmail', Vue.prototype.$keycloak.tokenParsed.email);
        this.$store.dispatch('setUsername', Vue.prototype.$keycloak.tokenParsed.preferred_username);
        this.$store.dispatch('setRoles', Vue.prototype.$keycloak.tokenParsed.realm_access.roles);
      } else {
        this.$store.dispatch('setAuthenticated', false);
        this.$store.dispatch('setToken', "");
        this.$store.dispatch('setEmail', "");
        this.$store.dispatch('setUsername', "");
        this.$store.dispatch('setRoles', "");
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