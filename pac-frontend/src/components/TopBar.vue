<template>
  <nav class="navbar is-primary navbar-expand-sm bg-dark navbar-dark justify-content-end" role="navigation" aria-label="main navigation">
    <div v-if="authenticated" class="px-5">
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

  </nav>
</template>

<script>
import Vue from "vue";

export default {
  name: "TopBar",
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