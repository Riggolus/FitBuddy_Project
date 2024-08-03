<template>
  <div id="homeNav">
    <!-- <button id="editAccount" v-on:click="toAccount">Edit Account</button> -->
<button id="accountProfile" v-on:click="toAccount" >Profile</button>
  </div>
  

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    

   
    
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
      this.$router.push({ name: 'profile', params: { account: this.account.id } });
    }
  }
}
</script>
