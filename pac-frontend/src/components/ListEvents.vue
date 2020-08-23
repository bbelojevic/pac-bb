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
            <th>Topics</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in events" v-bind:key="event.event.id">
            <td>{{event.event.name}}</td>
            <td>{{formatDate(event.event.startDate)}}</td>
            <td>{{formatDate(event.event.endDate)}}</td>
            <td>{{event.event.location.name}}</td>
            <td>
              <span v-for="(topic, index) in event.topics" v-bind:key="topic.id">
                <span>{{topic.name}}</span>
                <span v-if="index + 1 < event.topics.length">, </span>
              </span>
            </td>
            <td>
              <router-link :to="{ name: 'Event Timeline', params: { id:event.event.id }}" tag="button" class="btn btn-success">Go to Talks</router-link>
            </td>
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
      EventDataService.retrieveAllEventsAndTopics()
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