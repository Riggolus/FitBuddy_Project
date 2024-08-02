<template>
  <div id="homeNav">
    <button id="editAccount" v-on:click="toAccount">Edit Account</button>
  </div>

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    Photo Here**
    <h2>First Name Last Name</h2>
    <div>Email here**</div>
    <div>Profile (bio) here**</div>
    <div>Goals here**</div>

   
    
  </div>
</template>

<script>
import authService from '../services/AuthService';
import AccountService from '../services/AccountService';


export default {
  props: {
    account: {
      type: Object,
      required: true
    }
  },
  methods: {
    toAccount() {
      this.$router.push({ name: 'account' });
    }
  }
}
</script>
