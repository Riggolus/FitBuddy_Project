<template>
    <table id="users">
        <thead>
            <tr>
                <th>Username</th>
                <th>User ID</th>
                <th>Role</th>
                <th>Switch Role</th>
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
/* #users {
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    padding: 10px;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
} */

#users {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    font-family: Arial, sans-serif;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Styling for table headers */
#users thead th {
    background-color: #007BFF;
    color: #fff;
    padding: 12px 15px;
    text-align: left;
    font-weight: bold;
    border-bottom: 2px solid #0056b3;
}

/* Styling for table cells */
#users td {
    padding: 10px 15px;
    border-bottom: 1px solid #ddd;
}

/* Alternating row colors for better readability */
#users tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

#users tbody tr:hover {
    background-color: #f1f1f1;
}

/* Styling for input fields and selects */
#users input[type="text"],
#users input[type="number"],
#users select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

/* Styling for buttons */
.btnActivateDeactivate {
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
    margin: 2px;
    background-color: #007BFF; /* Blue for Switch Role */
    transition: background-color 0.3s ease, color 0.3s ease;
}

.btnActivateDeactivate:hover {
    background-color: #0056b3; /* Darker blue on hover */
}

</style>