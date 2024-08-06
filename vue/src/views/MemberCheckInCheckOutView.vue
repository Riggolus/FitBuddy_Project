<template>
    Check users in and out
    <table id="users">
        <thead>
            <tr>
                <th>Username</th>
                <th>User ID</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <input type="text" id="username" v-model="username">
                </td>
                <td>
                    <input type="number" id="userId" v-model="userId">
                </td>
                <td>
                    <select id="role" v-model="role">
                        <option value>All</option>   
                        <option value="checked_in">Checked In</option>
                        <option value="checked_out">Checked Out</option>
                    </select>
                </td>      
                
            </tr>
            <tr v-for="user in filteredUsers" :key="user.userId">
                <td>{{user.username}}</td>
                <td>{{user.userId}}</td>
                <td v-if="user.checkedStatus">Checked In</td>
                <td v-else>Checked Out</td>
                <td>
                    <button class="btnActivateDeactivate" v-on:click="updateUserRole(user)">Change Status</button>
                </td>
            </tr>
        </tbody>
    </table> 
</template>
<script>
import UserService from '../services/UserService';
import CheckInCheckOutService from '../services/CheckInCheckOutService';

export default {
    data(){
        return{
            users: [],
            username: '',
            userId: '',
            checkedStatus: '',
        };
    },
    computed: {
        filteredUsers() {
            let filteredUsers = this.users;
            if (this.username) {
                filteredUsers = filteredUsers.filter((user) => user.username
                .toLowerCase()
                .includes(this.username));
            }
            if (this.userId){
            filteredUsers = filteredUsers.filter((user)=> user.userId === this.userId);
           }
           if (this.checkedStatus){
            filteredUsers = filteredUsers.filter((user)=> user.checkedStatus === this.checkedStatus);
           }
            return filteredUsers;
        },
        
    },
    methods: {
        getUsers() {
            UserService.getUsers()
                .then((response) => {
                    this.users = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        }, getUserStatuses(){
            console.log("Getting user statuses");
            this.users.forEach(user => {
                console.log("Checking user status for: " + user.username);
                CheckInCheckOutService.getUserStatus(user)
                    .then(response => {
                    console.log("User status: " + response.data.checkedIn);
                    if(response.data.checkedIn !== null && response.data.checkedOut === null){
                        user.checkedStatus = true;
                        
                    } else {
                        user.checkedStatus = false;
                    }
                })
                .catch(error => {
                console.log(error);
          });
      });
    },

        },
        toggleUserStatus(user){
            if (user.checkedStatus) {
                this.checkUserOut(user);
            } else {
                this.checkUserIn(user);
            }
        },
        


        checkUserIn(user){
            UserService.checkUserIn(user)
            .then((response) => {
                this.getUsers();
                console.log("Did this work? " + user);
            })
            .catch((error) => {
                console.log(error);
            });
        },
        checkUserOut(user){
            UserService.checkUserOut(user)
            .then((response) => {
                this.getUsers();
                console.log("Did this work? " + user);
            })
            .catch((error) => {
                console.log(error);
            });
        },
    created() {
        this.getUsers();
        
    },
    mounted() {
        
        this.getUserStatuses();
    }
}


</script>