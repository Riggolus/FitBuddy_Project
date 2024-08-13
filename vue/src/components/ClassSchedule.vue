<template>
  <div class="container">
    <div class="calendar">
      <!-- Calendar Header -->
      <div class="heading">
        <div class="month">{{ currentMonth }}</div>
        <div class="btns">
          <div class="btn today-btn" @click="goToToday">Today</div>
          <div class="btn prev-btn" @click="prevWeek">Last</div>
          <div class="btn next-btn" @click="nextWeek">Next</div>
        </div>
      </div>

      <!-- Calendar Table -->
      <table>
        <thead class="weekdays">
          <tr>
            <th class="day" v-for="(date, index) in weekDates" :key="index">
              {{ formatDate(date) }}
            </th>
          </tr>
        </thead>
        <tbody class="days">
          <tr>
            <td
              v-for="(date, index) in weekDates"
              :key="index"
              :data-date="date"
              :class="{ today: isToday(date) }"
            >
              <div class="classes">
                <div
                  class="class-item" 
                  v-for="(classItem, classIndex) in getClassesForDate(date)"
                  :key="classIndex" 
                  @click="classDetails(classItem, date)"
                >
                  {{ classItem.className }} from {{ formatTime(classItem.startTime) }} till {{ formatTime(classItem.endTime) }}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Registration Panel with Transition -->
    <transition name="slide-fade" @before-enter="beforeEnter" @enter="enter" @leave="leave">
  <div class="registration" :class="{ show: isRegistrationVisible }">
    <h2>{{ currentClass.className }}</h2>
    <h3>Instructor: {{ currentClass.instructorName }}</h3>
    <p>{{ currentClass.description }}</p>
    
    <button class="register-btn" @click="registerForThisClass">Register</button>
    <button class="cancel-btn" @click="cancelRegistration">Cancel</button>
  </div>
</transition>

  </div>
</template>


  
<script>
import ClassScheduleService from '../services/ClassScheduleService';
import ClassRegistrationService from '../services/ClassRegistrationService';
  

export default {
  data() {
    return {
      classes: [],
      currentDate: new Date(),
      selectedClass: {
        className: '',
        sessionDate: ''
      },
      currentClass: {
        classId: 0,
        className: '',
        instructorName: '',
        description: '',
        totalCapacity: 0,
        currentCapacity: 0,
        sessionDate: '',
      },
      isRegistrationVisible: false, // Control visibility of registration
    };
  },
  computed: {
    currentMonth() {
      return this.currentDate.toLocaleString('default', { month: 'long' });
    },
    weekDates() {
      const startOfWeek = this.getStartOfWeek(this.currentDate);
      return Array.from({ length: 7 }, (_, i) => {
        const date = new Date(startOfWeek);
        date.setDate(startOfWeek.getDate() + i);
        return date;
      });
    },
  },
  mounted() {
    this.fetchClasses();
  },
  methods: {
    fetchClasses() {
      ClassScheduleService.getClassSchedule()
        .then((response) => {
          this.classes = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    hasClassPassed(date) {
      const now = new Date();
      return new Date(date) < now; // Checks if the class date is before the current date
    },
    
    getStartOfWeek(date) {
      const day = date.getDay();
      const diff = date.getDate() - day;
      return new Date(date.setDate(diff));
    },
    isToday(date) {
      const today = new Date();
      return (
        today.getFullYear() === date.getFullYear() &&
        today.getMonth() === date.getMonth() &&
        today.getDate() === date.getDate()
      );
    },
    getClassesForDate(date) {
      const dayOfWeek = date.getDay();
      const dayKeys = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'];
      const dayKey = dayKeys[dayOfWeek];
  
      return this.classes.filter(classItem => classItem[dayKey]);
    },
    prevWeek() {
      this.currentDate.setDate(this.currentDate.getDate() - 7);
      this.currentDate = new Date(this.currentDate);
    },
    nextWeek() {
      this.currentDate.setDate(this.currentDate.getDate() + 7);
      this.currentDate = new Date(this.currentDate);
    },
    goToToday() {
      this.currentDate = new Date();
    },
    formatDate(date) {
      const dayOfWeek = date.toLocaleDateString('default', { weekday: 'short' });
      const dayOfMonth = date.getDate();
      return `${dayOfWeek} ${dayOfMonth}`;
    },
    formatDateForServer(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
    formatTime(time) {
      let [hours, minutes] = time.split(':');
      hours = parseInt(hours);
      const ampm = hours >= 12 ? 'PM' : 'AM';
      hours = hours % 12 || 12;
      return `${hours}:${minutes} ${ampm}`;
    },
    
    classDetails(classItem, date){
      this.currentClass = {
        classId: classItem.classId,
        className: classItem.className,
        instructorName: classItem.instructorName,
        description: classItem.description,
        sessionDate: this.formatDateForServer(date)
      };
      this.selectedClass = {
        className: classItem.className,
        sessionDate: this.formatDateForServer(date)
      };
      this.isRegistrationVisible = true; // Show registration panel
    },
    registerForThisClass(){
      ClassRegistrationService.registerForClass(this.currentClass)
        .then(() => {
          alert("Registered for " + this.selectedClass.className);
          console.log("Registered for class");
          window.location.reload();
          this.isRegistrationVisible = false; // Hide registration panel after registering
        })
        .catch((error) => {
          console.log(error);
        });
    },
    cancelRegistration() {
      this.isRegistrationVisible = false; // Hide registration panel on cancel
    },
    beforeEnter(el) {
    el.style.transform = 'translateX(100%)';
    el.style.opacity = 0;
  },
  enter(el, done) {
    el.offsetWidth; // trigger reflow
    el.style.transition = 'transform 0.5s ease, opacity 0.5s ease';
    el.style.transform = 'translateX(0)';
    el.style.opacity = 1;
    done();
  },
  leave(el, done) {
    el.style.transition = 'transform 0.5s ease, opacity 0.5s ease';
    el.style.transform = 'translateX(100%)';
    el.style.opacity = 0;
    done();
  }

  },
};
</script>


<style scoped>
/* Global styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Montserrat", sans-serif;
}

body {
  background-color: lightgray;
}

/* Container and Calendar */
.container {
  width: 100%;
  min-height: 75vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.calendar {
  width: 100%;
  max-width: 900px;
  padding: 30px 20px;
  border-radius: 10px;
  background-color: white;
  
}

/* Calendar Heading */
.calendar .heading {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
}

.calendar .heading .month {
  font-size: 25px;
  font-weight: 600;
  color: black;
}

.calendar .heading .btns {
  display: flex;
  gap: 10px;
}

.calendar .heading .btns .btn {
  width: 60px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
  color: black;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.calendar .heading .btns .btn:hover {
  transform: scale(1.25);
}

/* Table and Table Headers */
table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed; /* Ensures columns are evenly spaced */
}

thead .weekdays {
  background-color: #f4f4f4;
}

thead .weekdays .day {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  padding: 10px;
  border-bottom: 2px solid #ccc;
}

tbody .days {
  display: flex;
}

tbody .days .day {
  text-align: center;
  vertical-align: top;
  padding: 20px;
  border: 1px solid #ccc;
  transition: all 0.2s ease-in-out;
}

tbody .days .day.today {
  background-color: lightgray;
}

tbody .days .day:hover {
  background-color: #e0e0e0;
  transform: scale(1.05);
}

/* Adjusts spacing for table columns */
th, td {
  width: 14.28%; /* Spreads columns evenly across the table */
}

/* Class items inside table cells */
.classes {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.class-item {
  background-color: #f8f8f8;
  padding: 5px;
  border-radius: 3px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
  font-size: 14px;
  text-align: center;
  transition: all 0.2s ease-in-out;
}

.class-item:hover {
  transform: scale(1.1); /* Slightly enlarges the item */
  z-index: 10; /* Ensures the hovered item stays above others */
  background-color: #e0e0e0; /* Optional: Change background color on hover */
  cursor: pointer;
}
.slide-enter-active, .slide-leave-active {
  transition: transform 0.5s ease;
}
.slide-enter {
  transform: translateX(100%); /* Starts outside the view */
}
.slide-leave-to {
  transform: translateX(100%); /* Ends outside the view */
}

/* Transition effects for sliding registration panel */
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: transform 0.5s ease, opacity 0.5s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

/* Registration form */
/* Registration form */
.registration {
  position: fixed; /* Make sure it overlays everything */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* Center the form */
  width: 300px;
  padding: 20px;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000; /* Make sure it is above the calendar and other content */
  display: none; /* Initially hidden */
}

/* Show the registration form when isRegistrationVisible is true */
.registration.show {
  display: block; /* Show the registration form */
}

/* Transition effects for sliding registration panel */
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: transform 0.5s ease, opacity 0.5s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(100%);
  opacity: 0;
}
/* Buttons */
button {
  border: none;
  padding: 10px 15px;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
  color: white;
  margin-right: 10px; /* Space between buttons */
  transition: background-color 0.3s ease;
}

button:hover {
  opacity: 0.9; /* Slightly transparent on hover */
}

/* Register Button */
button.register-btn {
  background-color: #28a745; /* Green */
}

button.register-btn:hover {
  background-color: #218838; /* Darker green */
}

/* Cancel Button */
button.cancel-btn {
  background-color: #dc3545; /* Red */
}

button.cancel-btn:hover {
  background-color: #c82333; /* Darker red */
}

</style>