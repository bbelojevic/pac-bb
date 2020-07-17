<template>
  <div>
    <h3>Location Details</h3>
    <div class="container">
      <form @submit="validateAndSubmit">
        <div v-if="errors.length">
          <div class="alert alert-warning" v-bind:key="index" v-for="(error, index) in errors">{{error}}</div>
        </div>
        <fieldset class="form-group">
          <label>Id</label>
          <input type="text" class="form-control" v-model="id" disabled>
        </fieldset>
        <fieldset class="form-group">
          <label>Name</label>
          <input type="text" class="form-control" v-model="name">
        </fieldset>
        <button class="btn btn-success" type="submit">Save</button>
      </form>
    </div>
  </div>
</template>

<script>
import LocationDataService from "../service/LocationDataService";

export default {
  name: "locationDetails",
  data() {
    return {
      name: "",
      errors: []
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    }
  },
  methods: {
    refreshLocationDetails() {
        LocationDataService.retrieveLocation(this.id).then(response => {
          this.name = response.data.name;
        });
    },
    validateAndSubmit(e) {
      e.preventDefault();
      
      this.errors = [];

      if (!this.name) {
        this.errors.push("Name can't be empty, please populate the field.");
      } else if (this.name.length < 2) {
        this.errors.push("Enter atleast 2 characters in Name field");
      }

      if(this.errors.length === 0) {
        let location = {
          id: this.id,
          name: this.name
        };

        LocationDataService.createOrUpdateLocation(location, this.id).then(() => {
          this.$router.push('/locations');
        });
      }
    }
  },
  created() {
    this.refreshLocationDetails();
  }
};

// https://router.vuejs.org/api/#router-link
// https://github.com/vuejs/vue-router/issues/2881
// this.$router.push({
//   name: 'about-too'
// }, () => {});
</script>

<style>

</style>