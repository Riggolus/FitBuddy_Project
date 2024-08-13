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
      <h1 class="welcome">Welcome {{ user.username }}</h1>
      <div class="content">
        <div class="calendar-container">
          <class-schedule />
        </div>
        <div class="upcoming-class-container">
          <user-upcoming-class />
        </div>
          <div class="current-workout">
            <current-workout />
          </div>
          <div class="achievements">
            <achievements />
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
import UserUpcomingClass from '../components/UserUpcomingClass.vue';

export default {
  components: {
    ClassSchedule,
    CreateClass,
    CurrentWorkout,
    Achievements,
    UserUpcomingClass
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
  grid-template-areas: 
    'calendar-container upcoming-class-container'
    'workout-details workout-details'
    'achievements achievements';
  grid-template-columns: 1fr 1fr; /* Use fractions to make sure it adapts to the page width */
  grid-template-rows: auto auto auto; /* Auto to adjust row height based on content */
  max-width: 100vw;
  max-height: 100vh;
  /* overflow: hidden; */
  gap: 20px;
  padding: 10px; /* Optional: add padding to avoid content touching the edges */
}

.calendar-container {
  grid-area: calendar-container;
  background-color: #f0f4f8;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.upcoming-class-container {
  grid-area: upcoming-class-container;
  background-color: #f0f4f8;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.current-workout {
  grid-area: workout-details;
  background-color: #f0f4f8;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.achievements {
  grid-area: achievements;
  background-color: #f0f4f8;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Adjustments to the container layout */
.container {
  display: grid;
  grid-template-areas: 
      "homeNav"
      "home";
  grid-template-columns: 1fr;
  grid-template-rows: auto 1fr;
  width: 100%;
  height: 100vh; /* Full viewport height */
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

/* Create Class Form Styling */
.create-class {
  position: absolute;
  top: 20px;
  right: -320px; /* Position it off-screen initially */
  width: 300px;
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 10;
  transition: right 0.3s ease, opacity 0.3s ease;
  opacity: 0;
}

.create-class.show {
  right: 20px;
  opacity: 1;
}

.create-class.hide {
  right: -320px;
  opacity: 0;
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

