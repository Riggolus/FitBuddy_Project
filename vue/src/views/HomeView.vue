<template>
  <div id="homeNav">
    <!-- <button id="editAccount" v-on:click="toAccount">Edit Account</button> -->
<button id="accountProfile" v-on:click="toAccount" >Profile</button>
  </div>
  

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this </p>

    <button id="check-in" v-on:click="checkUserIn">Check In</button>

    

    <h1 v-if="isAdminOrEmployee"><router-link v-bind:to="{name: 'userRole'}" >Check-in Member</router-link></h1>
    
    <h1 v-if="isAdmin"><router-link v-bind:to="{name: 'userRole'}">Add Employee</router-link></h1>
   
    
  </div>
</template>

<script>
import authService from '../services/AuthService';
import AccountService from '../services/AccountService';

import CheckInCheckOutService from '../services/CheckInCheckOutService';


export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user'))
    }
  },
  computed: {
    isAdmin(){
      return this.user.authorities.some((auth) => auth.name ==='ROLE_ADMIN');
    },
    isAdminOrEmployee(){
      return this.user.authorities.some((auth) => auth.name ==='ROLE_ADMIN' || auth.name ==='ROLE_EMPLOYEE');
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
    },
    checkUserIn() {
      console.log("this.user.username: ", this.user.username);
      CheckInCheckOutService.checkIn()
        .then(() => {
          console.log("Checked In");
          this.$router.push({ name: 'workout', params: { username: this.user.username } });
         })
         .catch((error) => {
           console.log(error);
         });
    }
  }
}
</script>
