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
  
  methods: {
    toAccount() {
      const userId = this.account.user_id; // Access user_id from account prop
      console.log("Account Object:", this.account); // Debugging line
      console.log("The user id: " + userId); // Debugging line
      if (userId) {
        this.$router.push({ name: 'profile', params: { id: userId } });
      } else {
        console.error("User ID is not available.");
      }
    }
  }
}
</script>
