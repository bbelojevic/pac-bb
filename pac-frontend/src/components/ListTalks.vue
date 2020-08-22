<template>
  <div class="container">
    <h3>Talks</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="container">
          <table class="table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Level</th>
            <th>Language</th>
            <th>People</th>
            <th>Topics</th>
            <th>Events</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="talk in talks" v-bind:key="talk._links.self.href">
            <td>{{talk.title}}</td>
            <td>{{talk.level.name}}</td>
            <td>{{talk.language.name}}</td>
            <td>
              <span v-for="(person, index) in talk.persons" v-bind:key="person.name">
                <span>{{person.name}}</span>
                <span v-if="index + 1 < talk.persons.length">, </span>
              </span>
            </td>
            <td>
              <span v-for="(topic, index) in talk.topics" v-bind:key="topic.name">
                <span>{{topic.name}}</span>
                <span v-if="index + 1 < talk.topics.length">, </span>
              </span>
            </td>
            <td>{{talk.event.name}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import TalkDataService from "../service/TalkDataService";

export default {
  name: "TalkList",
  data() {
    return {
      talks: [],
      message: null
    };
  },
  methods: {
    refreshTalks() {
      TalkDataService.retrieveAllTalks()
        .then(response => {
          this.talks = response.data;
        });
    },
  },
  created() {
    this.refreshTalks();
  }
};
</script>

<style>

</style>