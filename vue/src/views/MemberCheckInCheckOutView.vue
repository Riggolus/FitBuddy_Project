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
                <td>Checked Status</td>
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
            checkedIn: null,
            checkedOut: null,

            };
        },
    computed: {
        filteredUsers() {
            let filteredUsers = this.users;
            if (this.checkedIn) {
                filteredUsers = filteredUsers.filter((user) => user.checkedIn
                .toLowerCase()
                .includes(this.checkedIn));
            }
            if (this.userId){
            filteredUsers = filteredUsers.filter((user)=> user.userId === this.userId);
           }
           if (this.checkedOut){
            filteredUsers = filteredUsers.filter((user)=> user.checkedOut === this.checkedOut);
           }
            return filteredUsers;
        }
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
        }
    }, 
    mounted() {
        this.getUsers();
    }
}

</script>