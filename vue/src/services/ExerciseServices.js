import axios from 'axios';
import store from '../store';

const http = axios.create({
    baseURL: import.meta.env.VITE_REMOTE_API
  });

// Set default Authorization header for all requests
http.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    //const token = store.getters.authToken; // Get the token from Vuex store
    console.log(`Token being used in request: ${token}`); // Log the token
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  }, error => {
    return Promise.reject(error);
  });

export default {
    getExercises() {
        return http.get('/exercise/all')
    }
    // getExercise(id) {
    //     return http.get(`/exercise/${id}`)
    // },

}  