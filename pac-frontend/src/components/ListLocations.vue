<template>
  <div class="container">
    <h3>All Locations</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="row mb-3">
      <router-link :to="{ name: 'Location Details', params: { id: -1 }}" tag="button" class="btn btn-success">Add</router-link>
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
              <router-link :to="{ name: 'Location Details', params: { id: location.id }}" tag="button" class="btn btn-warning">Update</router-link>
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