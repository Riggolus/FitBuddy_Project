<template>
    <div class="container">
      <div class="calendar">
        <div class="heading">
          <div class="month">{{ currentMonth }}</div>
          <div class="btns">
            <div class="btn today-btn" @click="goToToday">Today</div>
            <div class="btn prev-btn" @click="prevWeek">Last</div>
            <div class="btn next-btn" @click="nextWeek">Next</div>
          </div>
        </div>
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
                  >
                    {{ classItem.className }} from {{ classItem.startTime }} till {{ classItem.endTime }}
                    
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  import ClassScheduleService from '../services/ClassScheduleService';
  
  export default {
    data() {
      return {
        classes: [], // This will hold your classes
        currentDate: new Date(), // Holds the current date
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
            console.log(this.classes);
          })
          .catch((e) => {
            console.log(e);
          });
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
        const dayOfWeek = date.getDay(); // 0 (Sunday) - 6 (Saturday)
        const dayKeys = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'];
        const dayKey = dayKeys[dayOfWeek];
  
        return this.classes.filter(classItem => classItem[dayKey]);
      },
      prevWeek() {
        this.currentDate.setDate(this.currentDate.getDate() - 7);
        this.currentDate = new Date(this.currentDate); // Force reactivity
      },
      nextWeek() {
        this.currentDate.setDate(this.currentDate.getDate() + 7);
        this.currentDate = new Date(this.currentDate); // Force reactivity
      },
      goToToday() {
        this.currentDate = new Date(); // Reset to today
      },
      formatDate(date) {
        const dayOfWeek = date.toLocaleDateString('default', { weekday: 'short' });
        const dayOfMonth = date.getDate();
        return `${dayOfWeek} ${dayOfMonth}`;
      },
    },
  };
  </script>

<style>
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
}
</style>