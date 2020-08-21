import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const EVENT_API_URL = `${BACKEND_API_URL}/api/events`;

class EventsDataService {
  
  retrieveAllEvents() {
    return axios.get(`${EVENT_API_URL}`, {
      transformResponse: [function (data) {  
        return data ? JSON.parse(data)._embedded.events : data;  
      }]
    });
  }

}

export default new EventsDataService();