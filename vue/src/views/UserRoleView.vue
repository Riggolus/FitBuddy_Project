<template>
    <table id="users">
        <thead>
            <tr>
                <th>Username</th>
                <th>User ID</th>
                <th>Role</th>
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
                        <option value="ROLE_USER">Member</option>
                        <option value="ROLE_EMPLOYEE">Employee</option>
                    </select>
                </td>      
                
            </tr>
            <tr v-for="user in filteredUsers" :key="user.userId">
                <td>{{user.username}}</td>
                <td>{{user.userId}}</td>
                <td>{{user.role}}</td>
                <td>
            <button class="btnActivateDeactivate" v-on:click="updateUserRole(user)">Switch Role</button>
          </td>
            </tr>
        </tbody>

    </table>
</template>
<script>
import UserService from '../services/UserService';

export default{
    data(){
        return{
            users: [],
            username: '',
            userId: '',
            role: ''
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
           if (this.role){
            filteredUsers = filteredUsers.filter((user)=> user.role.toUpperCase().includes(this.role));
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
        updateUserRole(user){
            UserService.updateUser(user)
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