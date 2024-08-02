import axios from 'axios';

const http = axios.create({
    baseURL: import.meta.env.VITE_REMOTE_API
  });

export default {
    createAccount(account) {
        return http.post('/account', account)
    },
    getAccount(id) {
        return http.get(`/account/${id}`)
    },
    updateAccount(id, account) {
        return http.put(`/account/${id}`, account)
    },
    deleteAccount(id) {
        return http.delete(`/account/${id}`)
    }
}