<template>
    <table id="users">
        <thead>
            <tr>
                <th>Username</th>
                <th>User ID</th>
                <th>Last Checked-in</th>
                <th>Last Checked-Out</th>
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
                <td></td>
                <td></td>
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
                <td>{{user.checkedIn}}</td>
                <td>{{user.checkedOut}}</td>
                <td>{{ user.checkedIn && user.checkedOut === null ? 'Checked In' : 'Checked Out'}}</td>
                <td>
                    <button class="btnActivateDeactivate" @click="checkMemberOut(user)" v-show="user.checkedIn && user.checkedOut === null">
                        Check-Out
                    </button>
                    <button class="btnActivateDeactivate" @click="checkMemberIn(user)" v-show="user.checkedOut !== null || user.checkedIn === null">
                        Check-In
                    </button>
                </td>
                <td>
                    <button class="viewUserHistory" @click="viewUserHistory(user)">
                        View History
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
            checkedIn: '',
            checkedOut: '',
            checkedStatus: '',
        };
    },
    computed: {
        filteredUsers() {
            return this.users.filter((user) => {
                const usernameMatch = !this.username || user.username.toLowerCase().includes(this.username.toLowerCase());
                const userIdMatch = !this.userId || user.userId === parseInt(this.userId);
                const statusMatch = this.checkedStatus === '' || (this.checkedStatus === 'true' && user.checkedIn && !user.checkedOut) || (this.checkedStatus === 'false' && (!user.checkedIn || user.checkedOut));
                return usernameMatch && userIdMatch && statusMatch;
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
        viewUserHistory(user){
            console.log("Viewing history for: " + user);
            this.$router.push({name: 'userAnalytics', params: {id: user.userId}});
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

<style scoped>
#users {
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    padding: 10px;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}

</style>