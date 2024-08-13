<template>
    <div id="create-account">
        <h1>Create Account</h1>
        <form v-on:submit.prevent="submitForm">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" v-model="editAccount.firstName"/>

            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" v-model="editAccount.lastName"/>

            <label for="email">Email</label>
            <input type="email" id="email" v-model="editAccount.email"/>

            <label for="photo">Photo</label>
            <input type="text" id="photo" v-model="editAccount.profilePicture"/>

            <label for="profile">Profile</label>
            <textarea id="profile" v-model="editAccount.profile"></textarea>

            <div class="button-group">
                <button type="submit" class="button-link">Create Account</button>
                <button type="reset" v-on:click="cancelForm" class="button-link">Cancel</button>
            </div>
        </form>
    </div>
</template>

<script>
    import AccountService from '../services/AccountService';

    export default {
        props: {
        account: {
            type: Object,
            required: true
        }
    },

    data() {
        return {
            editAccount: {
                email: this.account.email,
                firstName: this.account.firstName,
                lastName: this.account.lastName,
                profilePicture: this.account.profilePicture,
                profile: this.account.profile,
            },
            registrationErrors: false,
            registrationErrorMsg: 'There were problems submitting the info.'
        };
    },
    methods: {
        
        cancelForm() {
            this.$router.push({ name: 'home' });
        },
        submitForm() {
            console.log("Button works");
            console.log(this.editAccount);
            console.log(this.editAccount.profilePicture);
            console.log(localStorage.getItem('token')); 

            AccountService.createAccount(this.editAccount)
                .then(() => {
                    console.log("Account Created");
                    console.log("after account services" + this.editAccount.profilePicture);
                    this.$router.push({ name: 'home' });
                })
                .catch((error) => {
                    this.handleError(error);
                });
        },
        handleError(error) {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
                this.registrationErrorMsg = 'There were problems submitting the info.';
            } else {
                this.registrationErrorMsg = 'There was a problem with the server.';
            }
        
        }
    }
    }
</script>

<style scoped>
#form-container {
    display: grid;
    grid-template-areas: 
        "firstName lastName"
        "email email"
        "photo photo"
        "profile profile"
        "button button";
    grid-template-columns: 1fr 1fr;
    grid-gap: 10px;
    margin: 20px;
    padding: 20px;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}

form {
    display: grid;
    grid-template-areas: 
        "firstName lastName"
        "email email"
        "photo photo"
        "profile profile"
        "button button";
    grid-template-columns: 1fr 1fr;
    grid-gap: 10px;
}

label {
    font-weight: bold;
    color: #134B70;
}

input[type="text"],
input[type="email"],
textarea {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

textarea {
    resize: vertical;
}

.button-link {
    display: inline-block; 
    padding: 10px 20px; 
    background-color: #007bff; 
    color: white; 
    text-align: center; 
    text-decoration: none;
    border-radius: 5px; 
    border: none; 
    font-size: 16px;  
    font-weight: bold;
    transition: background-color 0.3s, border-color 0.3s;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    cursor: pointer;
}

.button-link:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.button-link:active {
    background-color: #00408d;
    border-color: #00408d;
}

button[type="reset"] {
    background-color: #dc3545;
    border: none;
}

button[type="reset"]:hover {
    background-color: #c82333;
}

button[type="reset"]:active {
    background-color: #bd2130;
}
</style>
