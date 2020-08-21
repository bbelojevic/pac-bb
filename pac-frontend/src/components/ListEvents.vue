<template>
  <div class="container">
    <h3>Events</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="container">
          <table class="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Start</th>
            <th>End</th>
            <th>Location</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in events" v-bind:key="event._links.self.href">
            <td>{{event.name}}</td>
            <td>{{formatDate(event.startDate)}}</td>
            <td>{{formatDate(event.endDate)}}</td>
            <td>{{event.location.name}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import EventDataService from "../service/EventDataService";
import moment from "moment";

export default {
  name: "EventList",
  data() {
    return {
      events: [],
      message: null
    };
  },
  methods: {
    refreshEvents() {
      EventDataService.retrieveAllEvents()
        .then(response => {
          this.events = response.data;
        });
    },
    formatDate(date) {
      return moment(date).format("DD. MM. YYYY.");
    },
  },
  created() {
    this.refreshEvents();
  }
};
</script>

<style>

</style>