<template>
  <div id="homeNav">
    <!-- Existing buttons -->
    <button id="accountProfile" class="button-link" @click="toAccount">
      <i class="fas fa-user"></i> Profile
    </button>      
    <button id="analytics" class="button-link" @click="toAnalytics">
      <i class="fas fa-chart-line"></i> Analytics/History
    </button>
    <button id="check-in" class="button-link" @click="checkUserIn" v-if="userStatus.checkOutTime !== null || userStatus.checkInTime === null">
      <i class="fas fa-dumbbell"></i> Check In</button>
    <button id="back-to-workout" class="button-link" @click="backToTraining" v-if="userStatus.checkInTime && userStatus.checkOutTime === null">
      <i class="fa-solid fa-people-pulling"></i> Continue Training</button>
    <button id="check-out" class="button-link" @click="checkUserOut" v-if="userStatus.checkInTime && userStatus.checkOutTime === null">
      <i class="fas fa-sign-out-alt"></i> Check Out
    </button>   
    <button v-if="isAdmin" class="button-link"><router-link :to="{ name: 'userRole' }" class="router">
      <i class="fas fa-user-plus"></i> Add Employee</router-link></button>
    <button v-if="isAdminOrEmployee" class="button-link"><router-link :to="{ name: 'checkInCheckOut' }" class="router">
      <i class="fa-solid fa-address-book"></i> Members</router-link></button>
    <button v-if="isAdminOrEmployee" class="button-link"><router-link :to="{ name: 'equipmentUsage' }" class="router">
      <i class="fa-solid fa-weight-hanging"></i> Equipment</router-link>
    </button>
  </div>
  
  <div class="container">
    <div class="home">
      <h1>Welcome {{ user.username }}</h1>
      
      <div class="content">
        <div class="calendar-container">
          <class-schedule />
        </div>
        <div class="workout-achievements">
          <div class="current-workout">
            <current-workout />
          </div>
          <div class="achievements">
            <achievements />
          </div>
        </div>
      </div>

      <button v-if="isAdminOrEmployee" :class="['toggle-form-btn', showForm ? 'cancel' : '']" @click="toggleForm">
        {{ showForm ? 'Cancel' : 'Add New Class' }}
      </button>
      <div :class="['create-class', showForm ? 'show' : 'hide']">
        <create-class @classCreated="addClass" />
      </div>
    </div>
  </div>
</template>

<script>
import CheckInCheckOutService from '../services/CheckInCheckOutService';
import ClassSchedule from '../components/ClassSchedule.vue';
import CreateClass from '../components/CreateClass.vue';
import CurrentWorkout from '../components/CurrentWorkout.vue';
import Achievements from '../components/Achievements.vue';

export default {
  components: {
    ClassSchedule,
    CreateClass,
    CurrentWorkout,
    Achievements
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user')),
      userStatus: {
        checkInTime: '',
        checkOutTime: ''
      },
      showForm: false // State for toggling the CreateClass form
    }
  },
  computed: {
    isAdmin() {
      return this.user.authorities.some((auth) => auth.name === 'ROLE_ADMIN');
    },
    isAdminOrEmployee() {
      return this.user.authorities.some((auth) => auth.name === 'ROLE_ADMIN' || auth.name === 'ROLE_EMPLOYEE');
    }
  },
  methods: {
    toAccount() {
      const currentUser = JSON.parse(localStorage.getItem('user'));
      if (currentUser) {
        this.$router.push({ name: 'profile', params: { username: currentUser } });
      } else {
        console.error("User ID is not available.");
      }
    },
    checkUserIn() {
      CheckInCheckOutService.checkIn()
        .then(() => {
          this.$router.push({ name: 'workout', params: { username: this.user.username } });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    backToTraining() {
      this.$router.push({ name: 'workout', params: { username: this.user.username } });
    },
    checkUserOut() {
      CheckInCheckOutService.checkOut()
        .then(() => {
          this.checkStatus();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    toAnalytics() {
      const currentUser = JSON.parse(localStorage.getItem('user'));
      if (currentUser) {
        this.$router.push({ name: 'analytics', params: { username: currentUser }, query: { isNavFromHome: true } });
      } else {
        console.error("User ID is not available.");
      }
    },
    checkStatus() {
      CheckInCheckOutService.getCurrentUserStatus()
        .then((response) => {
          this.userStatus = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    toggleForm() {
      this.showForm = !this.showForm;
    },
    addClass(newClass) {
      // Add the new class to your schedule or wherever necessary
      this.showForm = false; // Hide the form after adding a class
    }
  },
  created() {
    this.checkStatus();
  }
}
</script>

<style scoped>
.content {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  width: 100%;
  max-width: 1000px; /* Adjust max-width as needed */
  margin: 0 auto;
}

.current-workout,
.achievements {
  width: 100%;
  padding: 10px;
  background-color: #f0f4f8;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.calendar-container {
  grid-column: span 2;
  width: 100%;
  margin-top: 20px;
}
.container {
  display: grid;
  grid-template-areas: 
      "homeNav homeNav"
      "home home";
  grid-template-columns: 50% 50%;
  width: 1170px;
  background: #B9D7EA;
  border-radius: 10px;
  overflow: hidden;
}

#homeNav {
  grid-area: homeNav;
  display: flex;
  justify-content: space-evenly;
  padding: 1rem;
}

.home {
  grid-area: home;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 0rem 2rem;
  color: #134B70;
  position: relative;
}

.home > h1 {
  text-align: start;
}

.router {
  text-decoration: none;
  color: white;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.button-link {
  display: inline-block;
  padding: 5px;
  background-color: #007bff;
  color: white;
  text-align: center;
  text-decoration: none;
  border-radius: 5px;
  border: 1px solid #007bff;
  transition: background-color 0.3s, border-color 0.3s;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.button-link:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}

.button-link:active {
  background-color: #00408d;
  border-color: #00408d;
}

.calendar-container {
  position: relative;
}

/* Create Class Form Styling */
.create-class {
  position: absolute;
  top: 20px; /* Adjust based on the placement you need */
  right: -320px; /* Position it off-screen initially */
  width: 300px; /* Adjust width as needed */
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 10; /* Ensure it appears above other elements */
  transition: right 0.3s ease, opacity 0.3s ease;
  opacity: 0; /* Start as invisible */
}

/* Show the form */
.create-class.show {
  right: 20px; /* Move to visible position */
  opacity: 1; /* Make visible */
}

/* Hide the form */
.create-class.hide {
  right: -320px; /* Move off-screen */
  opacity: 0; /* Make invisible */
}

.toggle-form-btn {
  position: absolute;
  bottom: 20px;
  right: 20px;
  background-color: green;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.toggle-form-btn:hover {
  background-color: darkgreen;
}

.toggle-form-btn.cancel {
  background-color: red;
}

.toggle-form-btn.cancel:hover {
  background-color: darkred;
}
</style>

