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
  getEquipmentUsage(date) {
    return http.get(`/monthly-equipment-usage/specific-month/${date}`)
  },
  getEquipmentUsageCurrentMonth() {
    return http.get('/monthly-equipment-usage/current-month')
  }
}