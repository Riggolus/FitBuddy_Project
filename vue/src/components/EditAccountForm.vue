<template>
    <!-- Should change to 'Edit Account' and button to 'Save Changes' -->
     <!-- Form should be required to have all fields filled out -->
    <div id="container">
        <h1>Edit Account</h1>
   
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
            <input type="text" id="photo" v-model="editAccount.profilePicture"/>
        </div>
        <!--**************************************************************-->

        <div>
            <label for="profile">Profile</label>
            <textarea type="textbox" id="profile" v-model="editAccount.profile">
            </textarea>
        </div>
        <div>
            <!-- Potentially add a list of goals the user can check off as completed  -->
            <!-- <label for="goal">Goals</label>
            <textarea type="textbox" id="goal" v-model="editAccount.goals">
            </textarea> -->
        </div>

        <button type="submit" class="button-link">Save Changes</button>
        <button type="reset" v-on:click="cancelForm" class="button-link">Cancel</button>

    </form>
    </div>
    
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
                profilePicture: this.account.profilePicture,
                profile: this.account.profile,
                goals: this.account.goals
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
            console.log(localStorage.getItem('token')); 
            
            AccountService.updateAccount(this.account.id, this.editAccount)
                .then(() => {
                    console.log("Account Updated");
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
        
            
    },
    created() {
        this.isAuthenticated = !!localStorage.getItem('token'); // Check if the user is authenticated
            if (this.isAuthenticated) {
                AccountService.getMyAccount()
                    .then((response) => {
                    this.editAccount = response.data;
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
    }
}
</script>

<style scoped>
form {
    display: grid;
    grid-template-areas: 
        "first last"
        "firstName lastName"
        "email email"
        "photo photo"
        "profile profile"
        "goal goal"
        "button button";
    grid-template-columns: 1fr 1fr;
    grid-gap: 10px;
    margin: 10px;
    padding: 10px;
    /* border: 1px solid black; */
    /* border-radius: 5px; */
    /* background-color: #; */
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

}

#firstName {
    grid-area: firstName;
}

#lastName {
    grid-area: lastName;
}

#first {
    grid-area: first;
}

#last {
    grid-area: last;
}

#email {
    grid-area: email;
}

#photo {
    grid-area: photo;
}

#profile {
    grid-area: profile;
}

#goal {
    grid-area: goal;
}

.button-link {
    display: inline-block; 
    padding: 5px; 
    background-color: #007bff; 
    color: white; 
    text-align: center; 
    text-decoration: none;
    border-radius: 5px; 
    /* border: 1px solid #007bff;  */
    /* font-size: 16px;  */
    /* font-weight: bold; */
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