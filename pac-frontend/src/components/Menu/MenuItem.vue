<template>
    <ul v-if="displayRoute">
      <div class="navbar-item">
        <div class="buttons">
          <router-link exact-active-class="active" :to="{name: data.name}" tag="li" class="btn btn-warning float-left">
            <strong>{{data.meta.label || data.name}}</strong>
          </router-link>
        </div>
      </div>
    </ul>
</template>

<script>
export default {
  computed: {
    /**
     * check if the route can be displayed or not
     * @return {Boolean}
     */
    displayRoute() {
      if (this.data.meta.display === undefined || this.data.meta.display) {
        return true;
      }
      return false;
    }
  },
  name: "MenuItem",
  props: {
    data: Object
  }
};
</script>

<style scooped>

</style>