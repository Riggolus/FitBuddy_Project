<template>
  <div class="container">
    <div id="homeNav">
    <!-- <button id="editAccount" v-on:click="toAccount">Edit Account</button> -->
      <button id="accountProfile" class="button-link" v-on:click="toAccount" >Profile</button>
      <button id="analytics" class="button-link" v-on:click="toAnalytics" >Analytics/History</button>
      <button id="check-in" class="button-link" v-on:click="checkUserIn" v-if="userStatus.checkOutTime !== null || userStatus.checkInTime === null">Check In</button>
      <button id="check-out" class="button-link" v-on:click="checkUserOut" v-if="userStatus.checkInTime && userStatus.checkOutTime === null">Check Out Todo</button>
      <button v-if="isAdmin" class="button-link"><router-link v-bind:to="{name: 'userRole'}" class="router">Add Employee</router-link></button>
      <button v-if="isAdminOrEmployee" class="button-link"><router-link v-bind:to="{name: 'checkInCheckOut'}" class="router">Check-in Member</router-link></button>
  </div>
  

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this </p>


    <!-- <h1 v-if="isAdminOrEmployee"><router-link v-bind:to="{name: 'equipmentUsage'}">Equipment Usage</router-link></h1> -->
    
   
    
  </div>
  </div>
  
</template>

<script>
import authService from '../services/AuthService';
import AccountService from '../services/AccountService';

import CheckInCheckOutService from '../services/CheckInCheckOutService';


export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user')),
      userStatus: {
        checkInTime: '',
        checkOutTime: ''
      }
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
    },
    checkUserOut() {
      console.log("this.user.username: ", this.user.username);
      console.log("Checking out");
      CheckInCheckOutService.checkOut()
        .then(() => {
          console.log("Checked Out");
          this.checkStatus();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    toAnalytics() {
      const currentUser = JSON.parse(localStorage.getItem('user'));
      console.log("Account Object:", this.account); // Debugging line
      console.log("The user id: " + currentUser); // Debugging line
      if (currentUser) {
        this.$router.push({ name: 'analytics', params: { username: currentUser }, query: { isNavFromHome: true } });
      } else {
        console.error("User ID is not available.");
      }
    },
    checkStatus() {
      console.log("this.user.username: ", this.user.username);
      CheckInCheckOutService.getCurrentUserStatus()
        .then((response) => {
          console.log("Checked Status");
          this.userStatus = response.data;
          })
        .catch((error) => {
          console.log(error);
        });
    }
  },
  created() {
    this.checkStatus();
  }
}
</script>

<style scoped>
.container{
  display: grid;
  grid-template-areas: 
      "homeNav homeNav"
      "home home";
  grid-template-columns: 50% 50%;
  width: 1170px;
  background: #B9D7EA;
  border: #D6E6F2 solid 5px;
  border-radius: 10px;
  overflow: hidden;
  /* padding: 77px 55px 33px 55px; */
  box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    
}

#homeNav {
  grid-area: homeNav;
  display: flex;
  justify-content: space-evenly;
  padding: 1rem;
}

.home {
  grid-area: home;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 0rem 2rem;
  color: #134B70;
}

.home > h1 {
  text-align: start;
}

.router {
  text-decoration: none;
  color: white; 
  font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.button-link {
    display: inline-block; 
    padding: 5px; 
    background-color: #007bff; 
    color: white; 
    text-align: center; 
    text-decoration: none;
    border-radius: 5px; 
    border: 1px solid #007bff; 
    transition: background-color 0.3s, border-color 0.3s;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.button-link:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.button-link:active {
    background-color: #00408d;
    border-color: #00408d;
}

</style>