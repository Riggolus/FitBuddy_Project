<template> 
    <div class="workout-container">
      <table id="currentWorkout">
        <thead>
          <tr>
            <th class="name" id="e-name">Exercise</th>
            <th class="sets" id="e-sets">Sets</th>
            <th class="reps" id="e-reps">Reps</th>
            <th class="weight" id="e-weight">Weight</th>
            <th class="time" id="e-time">Duration</th>
          </tr>
        </thead>
        <tbody>
          <tr 
            v-for="(workout, index) in workouts" 
            :key="workout.exerciseName" 
            id="workoutData" 
            :class="{ 'odd-row': index % 2 !== 0 }"
          >
            <td class="name">{{ workout.exerciseName }}</td>
            <td class="sets">{{ workout.sets }}</td>
            <td class="reps">{{ workout.reps }}</td>
            <td class="weight">{{ workout.weight }}</td>
            <td class="time">{{ workout.duration }}</td>
          </tr>
        </tbody>
      </table>
  
      <table v-show="statsOn" class="stats-table">
        <thead>
          <tr>
            <th>Calories Burned</th>
            <th>Load Volume</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{ estimatedCalsExpended }} cals</td>
            <td>{{ loadVolume }} lbs</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import WorkoutService from '../services/WorkoutService';
  
  export default {
      props: {
          statsOn: {
              type: Boolean,
              default: true
          }
      },
      data() {
          return {
              workouts: [],
              currentDate: this.getCurrentDate(),
              exercise: {
                  exerciseName: ''
              }
          }
      },
      methods: {
          getCurrentWorkout() {
              WorkoutService.getWorkoutByDate(this.currentDate)
                  .then((response) => {
                      this.workouts = response.data;
                  })
                  .catch((error) => {
                      console.log(error);
                  });
          },
          getCurrentDate() {
              let date = new Date();
              let month = (date.getMonth() + 1).toString().padStart(2, '0');
              let day = date.getDate().toString().padStart(2, '0');
              let year = date.getFullYear();
              return `${year}-${month}-${day}`;
          }
      },
      computed: {
          estimatedCalsExpended() {
              return Math.round(
                  this.workouts.reduce((acc, workout) => {
                      return acc + ((4.25 * 3.5 * (workout.weight * 0.453592)) / 200) * (workout.sets * workout.reps);
                  }, 0)
              );
          },
          loadVolume() {
              return this.workouts.reduce((acc, workout) => {
                  return acc + workout.sets * workout.reps * workout.weight;
              }, 0);
          }
      },
      mounted(){
          this.getCurrentWorkout();
      },
      created() {
          this.getCurrentDate();
      }
  }
  </script>
  
  <style scoped>
  .workout-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f4f4f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  th, td {
    padding: 12px;
    border: 1px solid #ddd;
    text-align: center;
  }
  
  th {
    background-color: #134B70;
    color: #fff;
    border-radius: 10px;
  }
  
  .odd-row {
    background-color: #f0f0f0;
  }
  
  #e-name, #e-sets, #e-reps, #e-weight, #e-time {
    background-color: #134B70;
    color: white;
    border-radius: 10px;
  }
  
  #workoutData:hover {
    background-color: #ecf0f1;
    cursor: pointer;
  }
  
  .stats-table th {
    background-color: #3498db;
    color: #fff;
  }
  
  .stats-table td {
    background-color: #ecf0f1;
  }
  </style>
  