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
    createAccount(account) {
        return http.post('/account', account)
    },
    getAccount(id) {
        return http.get(`/account/user/${id}`)
    },
    getMyAccount(){
      return http.get('/account/myaccount');
    },
    updateAccount(id, account) {
        return http.put(`/account/update/${id}`, account)
    },
    deleteAccount(id) {
        return http.delete(`/account/${id}`)
    }
}