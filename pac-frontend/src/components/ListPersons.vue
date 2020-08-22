<template>
  <div class="container">
    <h3>People</h3>
    <div v-if="message" class="alert alert-success">
      {{message}}
    </div>
    <div class="container">
          <table class="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Organization</th>
            <th>Talks</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="person in persons" v-bind:key="person._links.self.href">
            <td>{{person.name}}</td>
            <td>{{person.organization.name}}</td>
            <td>
              <span v-for="(talk, index) in person.talks" v-bind:key="talk.title">
                <span>{{talk.title}}</span>
                <span v-if="index + 1 < person.talks.length"><br></span>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import PersonDataService from "../service/PersonDataService";

export default {
  name: "PersonList",
  data() {
    return {
      persons: [],
      message: null
    };
  },
  methods: {
    refreshPersons() {
      PersonDataService.retrieveAllPersons()
        .then(response => {
          this.persons = response.data;
        });
    },
  },
  created() {
    this.refreshPersons();
  }
};
</script>

<style>

</style>