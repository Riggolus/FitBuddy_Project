<template>
    <div class="create-class-form">
      <h2>Create a New Class {{ this.isSunday }}</h2>
      <form @submit.prevent="submitClass">
        <div class="form-group">
          <label for="name">Class Name:</label>
          <input v-model="className" id="name" type="text" required />
        </div>
        <div class="form-group">
          <label for="time">Start Time:</label>
          <input v-model="startTime" id="time" type="time" required />
        </div>
        <div class="form-group">
          <label for="time">End Time:</label>
          <input v-model="endTime" id="time" type="time" required />
        </div>
        <div class="form-group">
          <label for="description">Description:</label>
          <textarea v-model="description" id="description" required></textarea>
        </div>
        <div class="form-group">
          <label>Days:</label>
          <div class="days-checkboxes">
            <!-- <label><input type="checkbox" v-model="days.sunday" /> Sunday</label>
            <label><input type="checkbox" v-model="days.monday" /> Monday</label>
            <label><input type="checkbox" v-model="days.tuesday" /> Tuesday</label>
            <label><input type="checkbox" v-model="days.wednesday" /> Wednesday</label>
            <label><input type="checkbox" v-model="days.thursday" /> Thursday</label>
            <label><input type="checkbox" v-model="days.friday" /> Friday</label>
            <label><input type="checkbox" v-model="days.saturday" /> Saturday</label> -->
            <label><input type="checkbox" v-model="isSunday" /> Sunday</label>
            <label><input type="checkbox" v-model="isMonday" /> Monday</label>
            <label><input type="checkbox" v-model="isTuesday" /> Tuesday</label>
            <label><input type="checkbox" v-model="isWednesday" /> Wednesday</label>
            <label><input type="checkbox" v-model="isThursday" /> Thursday</label>
            <label><input type="checkbox" v-model="isFriday" /> Friday</label>
            <label><input type="checkbox" v-model="isSaturday" /> Saturday</label>
          </div>
        </div>
        <div class="form-group">
          <label for="recurrenceEnd">Recurrence End:</label>
          <input v-model="recurrenceEnd" id="recurrenceEnd" type="date" />
        </div>
        <button type="submit">Create Class</button>
      </form>
    </div>
  </template>
  
  <script>
  import ClassScheduleService from '../services/ClassScheduleService';
  import { formatDate, formatTime } from '../assets/utils.js';
  
  export default {
    data() {
      return {
        className: '',
        startTime: '',
        endTime: '',
        description: '',
        
        // days : {
        //   sunday: false,
        //   monday: false,
        //   tuesday: false,
        //   wednesday: false,
        //   thursday: false,
        //   friday: false,
        //   saturday: false,
        // },
        isMonday: false,
        isTuesday: false,
        isWednesday: false,
        isThursday: false,
        isFriday: false,
        isSaturday: false,
        isSunday: false,
        
        recurrenceEnd: '',
      };
    },
    methods: {
      submitClass() {
        // Convert startTime and endTime to military time
        const formattedStartTime = formatTime(new Date(`1970-01-01T${this.startTime}`));
        const formattedEndTime = formatTime(new Date(`1970-01-01T${this.endTime}`));
        
        // Convert recurrenceEnd to yyyy-mm-dd
        const formattedRecurrenceEnd = formatDate(new Date(this.recurrenceEnd));
        
        const newClass = {
          className: this.className,
          startTime: formattedStartTime,
          endTime: formattedEndTime,
          description: this.description,
        //   ...this.days,
            isMonday: this.isMonday,
            isTuesday: this.isTuesday,
            isWednesday: this.isWednesday,
            isThursday: this.isThursday,
            isFriday: this.isFriday,
            isSaturday: this.isSaturday,
            isSunday: this.isSunday,
          recurrenceEnd: formattedRecurrenceEnd,
        };
        console.log(newClass);
  
        ClassScheduleService.createClass(newClass)
          .then(() => {
            alert('Class created successfully!' + newClass.isSunday);
            this.resetForm();
          })
          .catch((e) => {
            console.log(e);
            alert('An error occurred while creating the class. ' + newClass.recurrenceEnd);
          });
        
        // Reset form fields
        this.resetForm();
      },
      resetForm() {
        this.className = '';
        this.startTime = '';
        this.endTime = '';
        this.description = '';
        
        this.isMonday = false;
        this.isTuesday = false;
        this.isWednesday = false;
        this.isThursday = false;
        this.isFriday = false;
        this.isSaturday = false;
        this.isSunday = false;

        
        this.recurrenceEnd = '';
      },
    },
  };
  </script>
  
  <style scoped>
  .create-class-form {
    max-width: 400px;
    margin: 0 auto;
  }
  .form-group {
    margin-bottom: 15px;
  }
  .days-checkboxes label {
    display: block;
    margin-bottom: 5px;
  }
  button {
    padding: 10px 15px;
    background-color: #007bff;
    color: #fff;
    border: none;
    cursor: pointer;
  }
  button:hover {
    background-color: #0056b3;
  }
  </style>
