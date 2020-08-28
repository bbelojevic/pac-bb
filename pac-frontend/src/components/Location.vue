<template>
  <div class="container">
    <h3>Location Details</h3>
    <div>
      <form @submit="validateAndSubmit">
        <div v-if="errors.length">
          <div class="alert alert-warning" v-bind:key="index" v-for="(error, index) in errors">{{error}}</div>
        </div>
        <fieldset class="form-group">
          <label>Name</label>
          <input type="text" class="form-control" v-model="locationName">
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
      locationName:  this.name,
      errors: []
    };
  },
  computed: {
    name() {
      return this.$route.params.name;
    },
    selfLink() {
      return this.$route.params.selfLink;
    },
  },
  methods: {
    refreshLocationDetails() {
      if (this.selfLink !== "" ) {
        LocationDataService.retrieveLocation(this.selfLink).then(response => {
          this.locationName = response.data.name;
        });
      }
    },
    validateAndSubmit(e) {
      e.preventDefault();

      this.errors = [];

      if (!this.locationName) {
        this.errors.push("Name can't be empty, please populate the field.");
      } else if (this.locationName.length < 2) {
        this.errors.push("Enter atleast 2 characters in Name field");
      }

      if(this.errors.length === 0) {
        let location = {
          name: this.locationName
        };

        LocationDataService.saveLocation(location, this.selfLink).then(() => {
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