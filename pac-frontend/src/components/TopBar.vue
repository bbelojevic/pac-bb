<template>
  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <Menu />

    <div class="mx-auto order-0">
        <a class="navbar-brand mx-auto" href="#">Bojana :)</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
      <div class="navbar-nav ml-auto">
        <div v-if="authenticated" class="px-5 my-auto">
          <strong class="text-warning">{{username}}</strong>
        </div>
        <div class="navbar-end">
          <div class="navbar-item">
            <div class="buttons">
              <a v-if="authenticated" class="btn btn-warning float-right" @click="logOut">
                <strong>Log out</strong>
              </a>
              <a v-else class="btn btn-warning float-right" @click="logIn">
                <strong>Log in</strong>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
 
</template>

<script>
import Vue from "vue";
import Menu from "../components/menu/";

export default {
  name: "TopBar",
  components: {
    Menu
  },
  data() {
    return {

    };
  },
  computed: {
    username () {
      return this.$store.state.user.username;
    },
    authenticated () {
      return this.$store.state.user.authenticated;
    }
  },
  methods: {
    logIn() {
        Vue.prototype.$keycloak.loginFn();
    },
    logOut() {
        Vue.prototype.$keycloak.logoutFn();
    },
  }
};
</script>

<style scoped>

</style>