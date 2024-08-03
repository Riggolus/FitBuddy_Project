import { createStore as _createStore } from 'vuex';
import axios from 'axios';

// Retrieve token and user data from localStorage
const currentToken = localStorage.getItem('token');
const currentUser = JSON.parse(localStorage.getItem('user'));

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {}
    },
    // Add account Object
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        // axios.defaults.headers.common = {};
        axios.defaults.headers.common['Authorization'] = '';
      }
    },
    // Adding getters to get token information
    getters: {
      authToken: (state) => state.token,
      currentUser: (state) => state.user
    }
  });
  return store;
}

// Create a default store instance and export it for use in other parts of the app
const storeInstance = createStore(currentToken, currentUser);
export default storeInstance;