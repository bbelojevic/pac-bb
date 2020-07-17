import Vue from "vue";
import Vuex from "vuex";
 
Vue.use(Vuex);
 
export default new Vuex.Store({
  state: {
    user: {
        authenticated: false,
        token: '',
        email: '',
        username: '',
        roles: []
    }
  },
  getters: {
    authenticated: state => {
      return state.user.authenticated;
    },
    username: state => {
      return state.user.username;
    },
    email: state => {
      return state.user.email;
    },
    roles: state => {
      return state.user.roles;
    }
  },
  mutations: {
    setAuthenticated (state, payload) {
      state.user.authenticated = payload;
    },
    setToken (state, payload) {
      state.user.token = payload;
    },
    setEmail (state, payload) {
      state.user.email = payload;
    },
    setUsername (state, payload) {
      state.user.username = payload;
    },
    setRoles (state, payload) {
      state.user.roles = payload;
    },
  },
  actions: {
    setAuthenticated (context, payload) {
      context.commit("setAuthenticated", payload);
    },
    setToken (context, payload) {
      context.commit("setToken", payload);
    },
    setEmail (context, payload) {
      context.commit("setEmail", payload);
    },  
    setUsername (context, payload) {
      context.commit("setUsername", payload);
    },
    setRoles (context, payload) {
      context.commit("setRoles", payload);
    },
  }
});