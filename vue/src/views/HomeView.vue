<template>
  <div id="homeNav">
    <!-- <button id="editAccount" v-on:click="toAccount">Edit Account</button> -->
<button id="accountProfile" v-on:click="toAccount" >Profile</button>
  </div>
  

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    
    <h1 v-if="isAdmin"><router-link v-bind:to="{name: 'userRole'}">Add Employee</router-link></h1>
   
    
  </div>
</template>

<script>
import authService from '../services/AuthService';
import AccountService from '../services/AccountService';


export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user'))
    }
  },
  computed: {
    isAdmin(){
      return this.user.authorities.some((auth) => auth.name ==='ROLE_ADMIN');
    }
  },
  methods: {
    
    toAccount() {
      const currentUser = JSON.parse(localStorage.getItem('user'));
      console.log("Account Object:", this.account); // Debugging line
      console.log("The user id: " + currentUser); // Debugging line
      if (currentUser) {
        this.$router.push({ name: 'profile', params: { username: currentUser } });
      } else {
        console.error("User ID is not available.");
      }
    }
  }
}
</script>
