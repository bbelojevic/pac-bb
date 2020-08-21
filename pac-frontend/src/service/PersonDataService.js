import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const PERSON_API_URL = `${BACKEND_API_URL}/api/persons`;

class PersonDataService {
  
  retrieveAllPersons() {
    return axios.get(`${PERSON_API_URL}`, {
      transformResponse: [function (data) {  
        return data ? JSON.parse(data)._embedded.persons : data;  
      }]
    });
  }

}

export default new PersonDataService();