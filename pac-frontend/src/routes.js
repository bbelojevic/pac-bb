import Vue from "vue";
import Router from "vue-router";
import store from './store';

// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location, onResolve, onReject) {
//   if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
//   return originalPush.call(this, location).catch(err => err)
// }

Vue.use(Router);

const router = new Router({
  mode: "history", // Use browser history
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => import("./components/ListLocations"),
      meta: {
        requireToBeAdmin: false,
        label: "Home", 
        display: true
      }
    },
    {
      path: "/locations",
      name: "Locations",
      component: () => import("./components/ListLocations"),
      meta: {
        requireToBeAdmin: false,
        label: "Locations",
        display: true
      }
    },
    {
      path: "/locations/:id",
      name: "Location Details",
      component: () => import("./components/Location"),
      meta: {
        requireToBeAdmin: true,
        label: "Location",
        display: false
      }
    },
    {
      path: "/events",
      name: "Events",
      component: () => import("./components/ListEvents"),
      meta: {
        requireToBeAdmin: false,
        label: "Events",
        display: true
      }
    },
    {
      path: "/people",
      name: "People",
      component: () => import("./components/ListPersons"),
      meta: {
        requireToBeAdmin: false,
        label: "People",
        display: true
      }
    },
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireToBeAdmin)) {
    if (store.getters.authenticated) {
      var roles = store.getters.roles;

      var adminRole = roles.filter(item => {
        return item === 'Admin';
      })

      // Vue.prototype.$keycloak.tokenParsed.realm_access.roles[0] === 'ADMIN'
      if (adminRole.length !== 0) {
        next();
      } else {
        next({ name: 'Home' }); // alert('You are not allow to go in the admin section');
      }
    } else {
      next({ name: 'Home' }); // alert('You are not authenticated');
    }
    //next(false)
  } else {
    next();
  }
})

export default router;