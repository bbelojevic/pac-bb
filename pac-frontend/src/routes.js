import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
  mode: "history", // Use browser history
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => import("./components/ListLocations")
    },
    {
      path: "/locations",
      name: "Locations",
      component: () => import("./components/ListLocations")
    },
    {
      path: "/locations/:id",
      name: "Location Details",
      component: () => import("./components/Location")
    }
  ]
});

export default router;