<template>
    test
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
        }
    },
}
</script>