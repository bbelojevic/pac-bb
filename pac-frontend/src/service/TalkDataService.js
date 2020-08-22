import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const TALK_API_URL = `${BACKEND_API_URL}/api/talks`;

class TalkDataService {
  
  retrieveAllTalks() {
    return axios.get(`${TALK_API_URL}`, {
      transformResponse: [function (data) {  
        return data ? JSON.parse(data)._embedded.talks : data;  
      }]
    });
  }

}

export default new TalkDataService();