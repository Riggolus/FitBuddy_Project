import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {
    getUsers() {
        return http.get('/users');
    },
    updateUser(user) {
        return http.put('user/update-role', user);
    }
}