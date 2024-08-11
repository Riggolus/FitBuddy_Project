<template>
    <div class="metrics-container">
      <div class="average-time">
        <h3>Average Time Per Workout: {{ formatDuration(this.averageTotalMinutesSpent) }}</h3>
      </div>
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Date of Workout</th>
              <th>Duration</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(metric, index) in metrics" :key="index" :class="{ 'odd-row': index % 2 !== 0 }">
              <td>{{ metric.dateVisitedGym }}</td>
              <td>{{ formatDuration(metric.totalMinutes) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  import WorkoutMetricsService from '../services/WorkoutMetricsService';
  
  export default {
      data(){
          return{
              userId: this.$route.params.id,
              metrics: [],
              averageTotalMinutesSpent: 0,
          }
      },
      methods: {
          getMetrics(){
              WorkoutMetricsService.getMemberMetricsForAllSessions()
              .then((response) => {
                  this.metrics = response.data;
                  this.getTotalAverageMinutesSpent();
              })
              .catch((error) => {
                  console.log(error);
              });
          }, 
          getTotalAverageMinutesSpent(){
              let avgTotalMinutes = 0;
              for (let i = 0; i < this.metrics.length; i++){
                  avgTotalMinutes += this.metrics[i].totalMinutes;
              }
              this.averageTotalMinutesSpent = avgTotalMinutes / this.metrics.length;
          },
          formatDuration(durationInMinutes){
              let hours = Math.floor(durationInMinutes / 60);
              let minutes = Math.floor(durationInMinutes % 60);
              return `${hours}h ${minutes}m`;
          }
      }, 
      created(){
          this.getMetrics();
      }
  }
  </script>
  
  <style scoped>
  .metrics-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f4f4f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .average-time {
    margin-bottom: 20px;
    text-align: center;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
  }
  
  .table-container {
    margin-top: 20px;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
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
  </style>
  