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
                    <select id="checked-in" v-model="checkedStatus">
                        <option value="">All</option>   
                        <option value="true">Checked In</option>
                        <option value="false">Checked Out</option>
                    </select>
                </td>      
                
            </tr>
            <tr v-for="user in filteredUsers" :key="user.userId">
                <td>{{user.username}}</td>
                <td>{{user.userId}}</td>
                <td >{{ user.checkedIn && user.checkedOut ? 'Checked In' : 'Checked Out'}}</td>
                <td>
                    <button class="btnActivateDeactivate" @click="checkMemberIn(user)">
                        Change Status In
                    </button>
                </td>
                <td>
                    <button class="btnActivateDeactivate" @click="checkMemberOut(user)">
                        Change Status Out
                    </button>
                </td>
            </tr>
        </tbody>
    </table> 
</template>
<script>

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
            return this.users.filter((user) => {
                return (
                    (!this.username || user.username.toLowerCase().includes(this.username.toLowerCase())) &&
                    (!this.userId || user.userId === parseInt(this.userId)) &&
                    (this.checkedStatus === '' || user.checkedStatus.toString() === this.checkedStatus)
                );
            });
        },
                
    },
    methods: {
        getUsers() {
            CheckInCheckOutService.getUserStatuses()
                .then((response) => {
                    this.users = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
            },
         
        
        toggleUserStatus(user){
            console.log("Button Pressed and user status: " + user.checkedIn);
            console.log("User: " + user);
            if (user.checkedIn) {
                this.checkMemberOut(user);
                
            } else {
                this.checkMemberIn(user);
            }
        },
        


        checkMemberIn(user){
            CheckInCheckOutService.checkUserIn(user)
            .then(() => {
                this.getUsers();
                console.log("checked in: " + user);
            })
            .catch((error) => {
                console.log(error);
            });
        },
        checkMemberOut(user){
            CheckInCheckOutService.checkUserOut(user)
            .then(() => {
                this.getUsers();
                console.log("Checked out: " + user);
            })
            .catch((error) => {
                console.log(error);
            });
        },
    },
    mounted() {
        this.getUsers();
        
    },
   
}


</script>

