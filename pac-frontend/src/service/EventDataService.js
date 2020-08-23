import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const EVENT_API_URL = `${BACKEND_API_URL}/api/events`;

class EventDataService {
  
  retrieveEvent(id) {
    return axios.get(`${EVENT_API_URL}/${id}`);
  }

  retrieveAllEvents() {
    return axios.get(`${EVENT_API_URL}`, {
      transformResponse: [function (data) {  
        return data ? JSON.parse(data)._embedded.events : data;  
      }]
    });
  }

  retrieveAllEventsAndTopics() {
    return axios.get(`${EVENT_API_URL}/search/getAllEventsWithTopics`);
  }

}

export default new EventDataService();