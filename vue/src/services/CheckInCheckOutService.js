import axios from 'axios';

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
    checkIn() {
        return http.post('/check/in')
    },
    checkOut() {
        return http.put('/check/out')
    },
    checkUserIn(user) {
        return http.post('/check/in/member', user)
    },
    checkUserOut(user) {
        return http.put('/check/out/member', user)
    },
    getUserStatus(user) {
        return http.get('/check/status', user)
    },
    getUserStatuses(){
      return http.get('/check/statuses')
    }
}