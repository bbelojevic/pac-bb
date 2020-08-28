import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const LOCATION_API_URL = `${BACKEND_API_URL}/api/locations`;

class LocationDataService {
 
  retrieveAllLocations() {
    return axios.get(`${LOCATION_API_URL}`, {
      transformResponse: [function (data) {  
        return data ? JSON.parse(data)._embedded.locations : data;  
      }]
    });
  }

  retrieveLocation(selfLink) {
    return axios.get(selfLink);
  }

  saveLocation(location, selfLink) {
    if (selfLink !== "" ) {
      return axios.put(selfLink, location);
    } else {
      return axios.post(`${LOCATION_API_URL}`, location);
    }
  }
  
  deleteLocation(selfLink) {
    return axios.delete(selfLink);
  }

  deleteLocationWithEventsAndTalks(selfLink) {
    var separator = LOCATION_API_URL + "/";
    var id = selfLink.split(separator).pop();

    return  axios.delete(`${LOCATION_API_URL}/deleteWithEventsAndTalks/${id}`);
  }

}

export default new LocationDataService();