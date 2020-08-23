<template>
  <div>
    <h3>Event Timeline for {{name}}</h3>
    <div class="container">
      <div>
        
      </div>
    </div>
  </div>
</template>

<script>
import EventDataService from "../service/EventDataService";

export default {
  name: "eventTimeline",
  data() {
    return {
      name: "",
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    }
  },
  methods: {
    refreshEventDetails() {
        EventDataService.retrieveEvent(this.id).then(response => {
          console.log(response);
          this.name = response.data.name;
        });
    },
  },
  created() {
    this.refreshEventDetails();
  }
};

</script>

<style>

</style>