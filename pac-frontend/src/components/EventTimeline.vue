<template>
  <div class="container">
    <h3>Event Timeline for {{name}}</h3>
    
    <v-row class="fill-height">
      <v-col>
        <v-sheet height="64">
          <v-toolbar
            flat
            color="white"
          >
            <v-btn
              outlined
              class="mr-4"
              color="grey darken-2"
              @click="setToday"
            >
              Today
            </v-btn>
            <v-btn
              fab
              text
              small
              color="grey darken-2"
              @click="prev"
            >
              <v-icon small>
                mdi-chevron-left
              </v-icon>
            </v-btn>
            <v-btn
              fab
              text
              small
              color="grey darken-2"
              @click="next"
            >
              <v-icon small>
                mdi-chevron-right
              </v-icon>
            </v-btn>
            <v-toolbar-title v-if="$refs.calendar">
              {{ $refs.calendar.title }}
            </v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="600">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="primary"
            type="category"
            category-show-all
            :start="startDate"
            :end="endDate"
            :categories="categories"
            :events="events"
            :event-color="getEventColor"
            @click:event="showEvent"
            @change="fetchEvents"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-y
          >
            <v-card
              color="grey lighten-4"
              min-width="250px"
              flat
            >
              <v-toolbar dense
                :color="selectedEvent.color"
                dark
              >
                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <h5 class="font-weight-light" v-html="selectedEvent.topics"></h5>
                <h5 class="font-weight-light" v-html="selectedEvent.persons"></h5>
                <h5 class="font-weight-light" v-html="selectedEvent.level"></h5>
              </v-card-text>
            </v-card>
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import EventDataService from "../service/EventDataService";

export default {
  name: "eventTimeline",
  data() {
    return {
      name: "",
      focus: "",
      events: [],
      colors: ["indigo", "deep-purple", "cyan", "green", "grey darken-1"],
      categories:"",
      talks: [],
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      startDate: new Date(),
      endDate: new Date(),
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    },
  },
  methods: {
    refreshEventDetails() {
      return EventDataService.retrieveEvent(this.id).then(response => {
        this.name = response.data.name;
        this.startDate = new Date(response.data.startDate);
        this.endDate = new Date(response.data.endDate);
      });
    },
    refreshRoomsForEvent() {
      return EventDataService.retrieveRoomsForEvent(this.id).then(response => {
        this.categories = response.data;
      });
    },
    refreshTalksForEvent() {
      return EventDataService.retrieveTalksForEvent(this.id).then(response => {
        this.talks = response.data;
      });
    },
    getEventColor (event) {
      return event.color;
    },
    setToday () {
      this.focus = "";
    },
    prev () {
      this.$refs.calendar.prev();
    },
    next () {
      this.$refs.calendar.next();
    },
    showEvent ({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => this.selectedOpen = true, 10);
      }

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10)
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    async fetchEvents () {
      await this.refreshEventDetails();
      await this.refreshRoomsForEvent();
      await this.refreshTalksForEvent();

      var events = [];

      for (let i = 0; i < this.talks.length; i++) {
        var talk = this.talks[i];

        var startTimestamp = talk.startDate;
        var start = new Date(startTimestamp);
        var endTimestamp = talk.endDate;
        var end = new Date(endTimestamp);

        var topics = "Topics: ";

        for (let j = 0; j < talk.topics.length; j++) {
          var topic = talk.topics[j];
          topics = topics + topic.name;
          
          if (j + 1 < talk.topics.length) {
            topics = topics + ", ";
          }
        }
        
        var persons = "People: ";

        for (let j = 0; j < talk.persons.length; j++) {
          var person = talk.persons[j];
          persons = persons + person.name;
          
          if (j + 1 < talk.persons.length) {
            persons = persons + ', ';
          }
        }

        var level = "Level: " + talk.level.name;

        events.push({
          name: talk.title,
          topics: topics,
          persons: persons,
          level: level,
          start: start,
          end: end,
          color: this.colors[this.rnd(0, this.colors.length - 1)],
          timed: true,
          category: talk.room.name,
        });
      }

      this.events = events;
    },
    rnd (a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
  beforeCreate() {
    //this.refreshEventDetails();
  },
  mounted () {
    this.$refs.calendar.checkChange()
  }
};

</script>

<style>
  /* .v-calendar .v-calendar-daily_head-day-label .v-btn {
    color:black;
  } */
</style>