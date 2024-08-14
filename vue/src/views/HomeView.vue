<template>
  <div id="border">

    <div id="homeNav">
      <button id="accountProfile" class="button-link" @click="toAccount">
        <i class="fas fa-user"></i> Profile
      </button>
      <button id="analytics" class="button-link" @click="toAnalytics">
        <i class="fas fa-chart-line"></i> Analytics/History
      </button>
      <button id="check-in" class="button-link" @click="checkUserIn"
        v-if="userStatus.checkOutTime !== null || userStatus.checkInTime === null">
        <i class="fas fa-dumbbell"></i> Check In</button>
      <button id="back-to-workout" class="button-link" @click="backToTraining"
        v-if="userStatus.checkInTime && userStatus.checkOutTime === null">
        <i class="fa-solid fa-people-pulling"></i> Continue Training</button>
      <button id="check-out" class="button-link" @click="checkUserOut"
        v-if="userStatus.checkInTime && userStatus.checkOutTime === null">
        <i class="fas fa-sign-out-alt"></i> Check Out
      </button>
      <button v-if="isAdmin" class="button-link"><router-link :to="{ name: 'userRole' }" class="router">
          <i class="fas fa-user-plus"></i> Add Employee</router-link></button>
      <button v-if="isAdminOrEmployee" class="button-link"><router-link :to="{ name: 'checkInCheckOut' }"
          class="router">
          <i class="fa-solid fa-address-book"></i> Members</router-link></button>
      <button v-if="isAdminOrEmployee" class="button-link"><router-link :to="{ name: 'equipmentUsage' }" class="router">
          <i class="fa-solid fa-weight-hanging"></i> Equipment</router-link>
      </button>
    </div>

    <div class="container">
      <div class="home">
        <h1 class="welcome">Welcome {{ account.firstName }}</h1>
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
  </div>


</template>

<script>
import CheckInCheckOutService from '../services/CheckInCheckOutService';
import ClassSchedule from '../components/ClassSchedule.vue';
import CreateClass from '../components/CreateClass.vue';
import CurrentWorkout from '../components/CurrentWorkout.vue';
import Achievements from '../components/Achievements.vue';
import UserUpcomingClass from '../components/UserUpcomingClass.vue';
import AccountService from '../services/AccountService';

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
      showForm: false,
      account: {}
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
      this.showForm = false;
    },
    getUserFirstName() {
      AccountService.getMyAccount()
        .then((response) => {
          this.account = response.data;
        })
    }
  },
  created() {
    this.checkStatus();
    this.getUserFirstName();
  }
}
</script>


<style scoped>
#border {
  border: #D6E6F2 solid 5px;
  background-color: #B9D7EA;
  border-radius: 10px;
  padding: 1rem;
}

.container {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #134B70;
  background: #B9D7EA;

}


.content {
  display: grid;
  grid-template-areas: ". welcome ."
    "calendar-container  calendar-container upcoming-class-container"
    "current-workout achievements upcoming-class-container";
  grid-template-columns: 1fr 1fr 1fr;
  margin: 10px;
  gap: 10px;
}

.welcome {
  grid-area: welcome;
  text-align: center;
  font-size: 3rem;
  margin: 10px;
}

.calendar-container {
  grid-area: calendar-container;
}

.upcoming-class-container {
  grid-area: upcoming-class-container;
}

.current-workout {
  grid-area: current-workout;
}

.achievements {
  grid-area: achievements;
}



#homeNav {
  display: flex;
  justify-content: space-evenly;
  padding: 1rem;
  background: #B9D7EA;
}

.home>h1 {
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

.create-class {
  position: absolute;
  top: 20px;
  right: -320px;
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

@media (max-width: 768px) {
  .content {
    display: grid;
    grid-template-areas:
      "welcome"
      "calendar-container"
      "upcoming-class-container"
      "current-workout"
      "achievements";
    grid-template-columns: 1fr;
    gap: 5px;
  }

  .welcome {
    font-size: 2rem;
    margin: 5px;
  }

  .calendar-container,
  .upcoming-class-container,
  .current-workout,
  .achievements {
    grid-area: auto;
    margin: 5px 0;
  }

  #homeNav {
    flex-direction: column;
    align-items: center;
  }

  .button-link {
    width: 100%;
    margin-bottom: 10px;
    font-size: 1rem;
    padding: 10px;
  }

  .create-class {
    position: relative;
    top: 0;
    right: 0;
    width: 100%;
    box-shadow: none;
    padding: 10px;
  }

  .toggle-form-btn {
    width: 100%;
    padding: 10px;
    font-size: 1rem;
  }
}
</style>
