import axios from "axios";

const BACKEND_API_URL = process.env.VUE_APP_BASE_URL;
const LOCATION_API_URL = `${BACKEND_API_URL}/locations`;

class LocationDataService {
  
  retrieveAllLocations() {
    return axios.get(`${LOCATION_API_URL}`);
  }

  retrieveLocation(id) {
    return axios.get(`${LOCATION_API_URL}/${id}`);
  }

  createOrUpdateLocation(location, id) {
    return axios.put(`${LOCATION_API_URL}/${id}`, location);
  }
  
  deleteLocation(id) {
    return axios.delete(`${LOCATION_API_URL}/${id}`);
  }

}

export default new LocationDataService();