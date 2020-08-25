<template>
  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <Menu />

    <div class="mx-auto order-0">
      <h3 class="navbar-brand mx-auto mb-0 h1">CONFERENCE</h3>
    </div>

    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
      <div class="px-2">
        <div class="navbar-item">
          <div class="buttons text-nowrap">
          <a class="btn btn-success" @click="swagger()">
            <strong>API</strong>
          </a>
        </div>
      </div>
      </div>

      <div class="navbar-nav ml-auto">
        <div v-if="authenticated" class="px-5 my-auto">
          <strong class="text-warning">{{username}}</strong>
        </div>
        <div class="navbar-end">
          <div class="navbar-item">
            <div class="buttons">
              <a v-if="authenticated" class="btn btn-warning float-right" @click="logOut()">
                <strong>Log out</strong>
              </a>
              <a v-else class="btn btn-warning float-right" @click="logIn()">
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
    swagger() {
      window.open(`${process.env.VUE_APP_BASE_URL}/api/swagger-ui/index.html`, "_blank");    
    }
  }
};
</script>

<style scoped>

  .navbar-item .buttons .btn {
    color:black;
  }

</style>