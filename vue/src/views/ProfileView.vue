<template>
    <div id="profileNav">
        <button id="editAccount" v-on:click="toAccount">Edit Account</button>
    </div>
    
    <div class="profile">
        <h1>Profile</h1>
        <p>You must be authenticated to see this</p>
        
        <div class="profileInfo">
        <img :src="account.photo" alt="Profile Picture" />
        <p>account.firstName  account.lastName</p>
        <p>account.email</p>
        <p>account.profile</p>
        <p>account.goal</p>
        </div>
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
                goal: ''
            }
        };
    },
    props: {
        account: {
            type: Object,
            required: true
        }
    },
    methods: {
        toAccount() {
            this.$router.push({ name: 'account' });
        },
        getAccount() {
            AccountService.getAccount()
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