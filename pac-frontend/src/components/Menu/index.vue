<template>
   <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
    <div v-for="(route,index) in routes" :key="index">
      <MenuItem :data="route"/>
    </div>
  </div>
</template>

<script>
import MenuItem from "./MenuItem";
export default {
  name: "Menu",
  components: {
    MenuItem
  },
  computed: {
    routes() {
      return this.$router.options.routes;
    }
  }
};
</script>

<style scoped>

</style>