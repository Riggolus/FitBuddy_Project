<template>
    <div class="profile" v-if="isAuthenticated">
        <h1>Profile</h1>
        
        <div class="profileInfo" v-if="userAccount">
            <img :src="userAccount.photo" alt="Profile Picture" />
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
import authService from '../services/AuthService';
import AccountService from '../services/AccountService';

export default {
    data() {
        return {
            userAccount: {
                firstName: '',
                lastName: '',
                email: '',
                photo: '',
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