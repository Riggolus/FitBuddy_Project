<template>
    <table id="users">
        <thead>
            <tr>
                <th>Username</th>
                <th>User ID</th>
                <th>Last Checked-in</th>
                <th>Last Checked-Out</th>
                <th>Status</th>
                <th>Check-In/Out</th>
                <th>View History</th>
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
                <td>{{ user.username }}</td>
                <td>{{ user.userId }}</td>
                <td>{{ user.checkedIn }}</td>
                <td>{{ user.checkedOut }}</td>
                <td>{{ user.checkedIn && user.checkedOut === null ? 'Checked In' : 'Checked Out' }}</td>
                <td>
                    <button class="btnActivateDeactivate" id="checkOut" @click="checkMemberOut(user)"
                        v-show="user.checkedIn && user.checkedOut === null">
                        Check-Out
                    </button>
                    <button class="btnActivateDeactivate" id="checkIn" @click="checkMemberIn(user)"
                        v-show="user.checkedOut !== null || user.checkedIn === null">
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
    data() {
        return {
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


        toggleUserStatus(user) {
            console.log("Button Pressed and user status: " + user.checkedIn);
            console.log("User: " + user);
            if (user.checkedIn) {
                this.checkMemberOut(user);

            } else {
                this.checkMemberIn(user);
            }
        },
        viewUserHistory(user) {
            console.log("Viewing history for: " + user);
            this.$router.push({ name: 'userAnalytics', params: { id: user.userId } });
        },


        checkMemberIn(user) {
            CheckInCheckOutService.checkUserIn(user)
                .then(() => {
                    this.getUsers();
                    console.log("checked in: " + user);
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        checkMemberOut(user) {
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
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    font-family: Arial, sans-serif;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

#users thead th {
    background-color: #007BFF;
    color: #fff;
    padding: 12px 15px;
    text-align: left;
    font-weight: bold;
    border-bottom: 2px solid #0056b3;
}

#users td {
    padding: 10px 15px;
    border-bottom: 1px solid #ddd;
}

#users tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

#users tbody tr:hover {
    background-color: #f1f1f1;
}

#users input[type="text"],
#users input[type="number"],
#users select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

.btnActivateDeactivate {
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
    margin: 2px;
    transition: background-color 0.3s ease, color 0.3s ease;
}

#checkOut {
    background-color: #dc3545;
}

#checkOut:hover {
    background-color: #c82333;
}


#checkIn {
    background-color: #28a745;
}

#checkIn:hover {
    background-color: #218838;
}

.viewUserHistory {
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
    background-color: #17a2b8;
    transition: background-color 0.3s ease;
}

.viewUserHistory:hover {
    background-color: #138496;
}

.btnActivateDeactivate[hidden],
.viewUserHistory[hidden] {
    display: none;
}
</style>