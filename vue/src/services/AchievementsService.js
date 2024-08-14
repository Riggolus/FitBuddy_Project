import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

// Set default Authorization header for all requests
http.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  console.log(`Token being used in request: ${token}`);
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

export default {
  createAchievements(achievements) {
    return http.post('/achievements/create', achievements);
  },
  viewMyAchievements() {
    return http.get('/achievements/all')
  },
  updateAchievement(achievements) {
    return http.put('achievements/update', achievements)
  },
  deleteAchievement(goalId) {
    return http.delete(`achievements/delete/${goalId}`)
  },
  viewSpecificAchievement(goalId) {
    return http.get(`achievements/${goalId}`)
  }
}