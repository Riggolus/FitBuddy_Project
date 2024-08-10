<template>
    <div class="profile" v-if="isAuthenticated">
        <h1>Profile</h1>
        
        
        <div class="profileInfo" v-if="userAccount">
            <img :src="userAccount.profilePicture" alt="Profile Picture" />
            <p>{{ userAccount.firstName }} {{ userAccount.lastName }}</p>
            <p>Email: {{ userAccount.email }}</p>
            <p>{{ userAccount.profile }}</p>
            <p>Goals: {{ userAccount.goals }}</p>
        </div>
        <div id="profileNav">
            <button id="editAccount" v-on:click="toAccount">Edit Account</button>
        </div>
    </div>
    <div v-else>
        <p>You must be authenticated to see this</p>
    </div>
</template>

<script>
import AccountService from '../services/AccountService';

export default {
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
/* .profile {
    display: grid;
    grid-template-areas: 
        "profileNav"
        "h1"
        "profileInfo";
    max-width: 1400px;
    margin: 0 auto;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    padding: 0 2rem 2rem 2rem;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h1 {
    grid-area: h1;
    display: flex;
    justify-content: center;
    color: #134B70;
    margin-bottom: 1.5rem;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.profileInfo {
    grid-area: profileInfo;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}

#profileInfoNoPfp {
    display: grid;
    grid-template-areas: 
        "name profile goals"
        "email profile goals";
    grid-template-columns: 1fr;
    padding: 1rem;
}

#pfp {
    grid-area: pfp;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
    overflow: hidden;
}

p {
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
#name {
    grid-area: name;
}
#email {
    grid-area: email;
}
#profile {
    grid-area: profile;
    padding: 0 1rem 0 1rem;
}

#goals {
    grid-area: goals;
    padding: 0 1rem 0 1rem;
}
#profileNav {
    grid-area: profileNav;
    display: flex;
    justify-content: space-evenly;
    padding: 1rem;
}
#editAccount {
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

#editAccount:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

#editAccount:active {
    background-color: #00408d;
    border-color: #00408d;
} */

</style>