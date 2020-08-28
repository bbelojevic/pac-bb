<template>
  <div class="container">
    <h3>Locations</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="row mb-3">
      <router-link v-if="authenticated" :to="{ name: 'Location Details', params: { name: '', selfLink: '' }}" tag="button" class="btn btn-success">Add</router-link>
      <router-link v-else :to="{ name: 'Location Details', params: { name: '', selfLink: '' }}" tag="button" class="btn btn-success" disabled>Add</router-link>
    </div>
    <div class="container">
          <table class="table">
        <thead>
          <tr>
            <th>Name</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="location in locations" v-bind:key="location.name">
            <td>{{location.name}}</td>
            <td>
              <router-link v-if="authenticated" :to="{ name: 'Location Details', params: { name: location.name, selfLink: location._links.self.href }}" tag="button" class="btn btn-warning">Update</router-link>
              <router-link v-else :to="{ name: 'Location Details', params: { name: location.name, selfLink: location._links.self.href }}" tag="button" class="btn btn-warning" disabled>Update</router-link>
            </td>
            <td>
              <button v-if="authenticated" class="btn btn-secondary" v-on:click="deleteLocationClicked(location.name, location._links.self.href)">Delete</button>
              <button v-else class="btn btn-secondary" v-on:click="deleteLocationClicked(location.name, location._links.self.href)" disabled>Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import LocationDataService from "../service/LocationDataService";
import store from '../store';

export default {
  name: "LocationList",
  data() {
    return {
      locations: [],
      message: null,
      httpStatus: null
    };
  },
  computed: {
    authenticated: {
      get() {
        return store.getters.authenticated;
      }
    },
  },

  methods: {
    refreshLocations() {
      LocationDataService.retrieveAllLocations()
        .then(response => {
          this.locations = response.data;
        });
    },
    deleteLocationClicked(name, selfLink) {
      LocationDataService.deleteLocationWithEventsAndTalks(selfLink).then(response => {
        this.message = `Delete of location ${name} Successful`;
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