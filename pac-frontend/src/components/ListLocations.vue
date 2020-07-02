<template>
  <div class="container">
    <h3>All Locations</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="row mb-3">
      <button class="btn btn-success" v-on:click="addLocationClicked()">Add</button>
    </div>
    <div class="container">
          <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Location Name</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="location in locations" v-bind:key="location.id">
            <td>{{location.id}}</td>
            <td>{{location.name}}</td>
            <td>
              <button class="btn btn-warning" v-on:click="updateLocationClicked(location.id)">Update</button>
            </td>
            <td>
              <button class="btn btn-secondary" v-on:click="deleteLocationClicked(location.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import LocationDataService from "../service/LocationDataService";

export default {
  name: "LocationList",
  data() {
    return {
      locations: [],
      message: null,
      httpStatus: null
    };
  },
  methods: {
    refreshLocations() {
      LocationDataService.retrieveAllLocations()
        .then(response => {
          this.locations = response.data;
        });
    },
    addLocationClicked() {
      this.$router.push(`/locations/-1`);
    },
    updateLocationClicked(id) {
      this.$router.push(`/locations/${id}`);
    },
    deleteLocationClicked(id) {
      LocationDataService.deleteLocation(id).then(response => {
        this.message = `Delete of location ${id} Successful`;
        this.httpStatus = response.data;
        this.refreshLocations();
      });
    }
  },
  created() {
    this.refreshLocations();
  }
};
</script>

<style>

</style>