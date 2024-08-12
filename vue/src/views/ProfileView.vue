<template>
    <div class="profile" v-if="isAuthenticated">
      <h1>Profile</h1>
      <div class="profileInfo" v-if="userAccount">
        <!-- Flex container to position profile picture and text side by side -->
        <div class="profileContent">
          <!-- Container for the circular profile picture -->
          <div class="profilePictureContainer">
            <img :src="userAccount.profilePicture" alt="Profile Picture" id="pfp" />
          </div>
          <!-- Profile details to the right of the picture -->
          <div class="profileDetails">
            <p class="name">{{ userAccount.firstName }} {{ userAccount.lastName }}</p>
            <p class="email">Email: {{ userAccount.email }}</p>
            <p>{{ userAccount.profile }}</p>
          </div>
        </div>
      </div>
      <div id="profileNav">
        <button id="editAccount" v-on:click="toAccount">
            <i class="fa-solid fa-cog"></i> Edit Account</button>
      </div>
      <div class="metrics-achievements">
        <average-visit-metrics />
        <achievements />
      </div>
    </div>
    <div v-else>
      <p>You must be authenticated to see this</p>
    </div>
  </template>
  
  

<script>
import AccountService from '../services/AccountService';
import AverageVisitMetrics from '../components/AverageVisitMetrics.vue';
import Achievements from '../components/Achievements.vue';

export default {
    components: {
        AverageVisitMetrics,
        Achievements
    },
    data() {
        return {
            userAccount: {
                firstName: '',
                lastName: '',
                email: '',
                profilePicture: '',
                profile: '',
                goals: ''
            },
            isAuthenticated: false,
            
        };
    },
    methods: {
        toAccount() {
            this.$router.push({ name: 'editAccount' });
        },
        getAccount() {
            AccountService.getMyAccount()
                .then((response) => {
                    this.userAccount = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    },
    created() {

         this.isAuthenticated = !!localStorage.getItem('token'); // Check if the user is authenticated
         if (this.isAuthenticated) {
            AccountService.getMyAccount()
                .then((response) => {
                    this.userAccount = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }
}
</script>

<style scoped>
.profile {
    max-width: 1400px;
    margin: 0 auto;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h1 {
    color: #134B70;
    margin-bottom: 1.5rem;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.profileInfo {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 2rem; /* Add space below the profile info */
}

.profileContent {
    display: flex;
    align-items: center; /* Align items vertically centered */
    gap: 1.5rem; /* Adjust gap between picture and details */
}

.profilePictureContainer {
    width: 160px; /* Slightly larger than the profile picture */
    height: 160px;
    border-radius: 50%;
    border: 5px solid #007bff; /* Circular border */
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    background-color: #fff; /* Optional background color */
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

#pfp {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover; /* Ensures the image covers the circle */
}

.profileDetails {
    display: flex;
    flex-direction: column;
    align-items: flex-start; /* Align text to the left */
}

.name {
    font-size: 1.25rem; /* Larger font size for name */
    font-weight: bold;
}

.email {
    color: #134B70; /* Match color with the title */
}

p {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin: 0.25rem 0; /* Add vertical spacing between paragraphs */
}

#profileNav {
    display: flex;
    justify-content: center;
    padding: 1rem;
}

#editAccount {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    border: 1px solid #007bff;
    transition: background-color 0.3s, border-color 0.3s;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

#editAccount:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

#editAccount:active {
    background-color: #00408d;
    border-color: #00408d;
}

.metrics-achievements {
    display: flex;
    justify-content: space-between;
    gap: 2rem; /* Adjust the gap between components */
    margin-top: 2rem;
}

.metrics-achievements > * {
    flex: 1; /* Allow both components to take up equal width */
    background-color: #fff; /* Add a background to separate components visually */
    padding: 1rem;
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}
</style>
