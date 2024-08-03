<template>
    <!-- Should change to 'Edit Account' and button to 'Save Changes' -->
     <!-- Form should be required to have all fields filled out -->
    <h1>{{ isEditing ? 'Edit Account' : 'Create Account' }}</h1>
    <form v-on:submit.prevent="submitForm">
        <label for="firstName">First Name</label>
        <input type="text" id="firstName" v-model="editAccount.firstName"/>
        <label for="lastName">Last Name</label>
        <input type="text" id="lastName" v-model="editAccount.lastName"/>
        <div>    
            <label for="email">Email</label>
            <input type="email" id="email" v-model="editAccount.email"/>
        </div>
        <!--**************************************************************-->
        <!-- Type should be 'file' but v-model doesn't support it -->
        <div>
            <label for="photo">Photo</label>
            <input type="text" id="photo" v-model="editAccount.photo"/>
        </div>
        <!--**************************************************************-->

        <div>
            <label for="profile">Profile</label>
            <textarea type="textbox" id="profile" v-model="editAccount.profile">
            </textarea>
        </div>
        <div>
            <!-- Potentially add a list of goals the user can check off as completed  -->
            <label for="goal">Goals</label>
            <textarea type="textbox" id="goal" v-model="editAccount.goal">
            </textarea>
        </div>

        <button type="submit">{{ isEditing ? 'Save Changes' : 'Create Account' }}</button>
        <button type="reset" v-on:click="cancelForm">Cancel</button>

    </form>
</template>

<script>
console.log(localStorage.getItem('token'));

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
                profilePicture: this.account.photo,
                profile: this.account.profile,
                goals: this.account.goal
            },
            registrationErrors: false,
            registrationErrorMsg: 'There were problems submitting the info.'
        };
    },
// Can be adjusted
    computed: {
        isEditing() {
            if (AccountService.getAccount === null) {
                return false;
            } else {
                return true;
            }
        }
    },
    methods: {
        
        cancelForm() {
            this.$router.push({ name: 'home' });
        },
        submitForm() {
            console.log("Button works");
            console.log(this.editAccount);
            console.log(localStorage.getItem('token')); 

            if (this.isEditing) {
                // Update existing account
                AccountService.updateAccount(this.account.id, this.editAccount)
                    .then(() => {
                        console.log("Account Updated");
                        this.$router.push({ name: 'home' });
                    })
                    .catch((error) => {
                        this.handleError(error);
                    });
            } else {
                // Create new account
                AccountService.createAccount(this.editAccount)
                    .then(() => {
                        console.log("Account Created");
                        this.$router.push({ name: 'home' });
                    })
                    .catch((error) => {
                        this.handleError(error);
                    });
            }
        },
        handleError(error) {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
                this.registrationErrorMsg = 'There were problems submitting the info.';
            } else {
                this.registrationErrorMsg = 'There was a problem with the server.';
            }
        
    


            // AccountService
            //     .createAccount(this.editAccount)
            //     .then(() => {
                    
            //         console.log("Account Created");
            //         this.$router.push({ name: 'home' });
                    
            //     })
            //     .catch((error) => {
            //         const response = error.response;
            //         this.registrationErrors = true;
            //         if (response.status == 400) {
            //             this.registrationErrorMsg = 'There were problems submitting the info.';
            //         } else {
            //             this.registrationErrorMsg = 'There was a problem with the server.';
            //         }
            //     });
        }
    }
}
</script>