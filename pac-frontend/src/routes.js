import Vue from "vue";
import Router from "vue-router";

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
      }
    },
    {
      path: "/locations",
      name: "Locations",
      component: () => import("./components/ListLocations"),
      meta: {
        requireToBeAdmin: false,
      }
    },
    {
      path: "/locations/:id",
      name: "Location Details",
      component: () => import("./components/Location"),
      meta: {
        requireToBeAdmin: true,
      }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireToBeAdmin)) {
    if (Vue.prototype.$keycloak.authenticated) {
      var roles = Vue.prototype.$keycloak.tokenParsed.realm_access.roles;

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