<template>
  <div class="metrics-container">
    <h2>Your Metrics</h2>
  
      <h3>Workout History</h3>
      <table class="metrics-table">
        <thead>
          <tr>
            <th>Date of Workout</th>
            <th>Calories</th>
            <th>Duration</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(metric, index) in groupedMetrics"
            :key="index"
            @click="selectMetric(metric)"
            class="metric-row"
            :class="{ 'odd-row': index % 2 !== 0 }"
          >
            <td>{{ metric.date }}</td>
            <td>{{ calsPerWorkout(metric.date) }} cals</td>
            <td>{{ formatDuration(metric.durationInMinutes) }}</td>
          </tr>
        </tbody>
      </table>
  
      <div v-show="showWorkout">
        <h3>Workout Details</h3>
        <table class="workout-details-table">
          <thead>
            <tr>
              <th>Exercise</th>
              <th>Sets</th>
              <th>Reps</th>
              <th>Weight</th>
              <th>Duration</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(workout, index) in workouts"
              :key="workout.exerciseName"
              :class="{ 'odd-row': index % 2 !== 0 }"
            >
              <td>{{ workout.exerciseName }}</td>
              <td>{{ workout.sets }}</td>
              <td>{{ workout.reps }}</td>
              <td>{{ workout.weight }} lbs</td>
              <td>{{ formatDuration(workout.duration) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <div>
        <h3>Stats</h3>
        <table class="stats-table">
          <thead>
            <tr>
              <th>Total Calories Burned</th>
              <th>Total Load Volume</th>
              <th>Total Duration</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ totalCalsExpended }} cals</td>
              <td>{{ totalLoadVolume }} lbs</td>
              <td>{{ formatDuration(totalDuration) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    
    <div>
      <h3>Calories Burned Over Time</h3>
      <canvas id="caloriesChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables, TimeScale, LinearScale } from 'chart.js';
import WorkoutMetricsService from '../services/WorkoutMetricsService';
import WorkoutService from '../services/WorkoutService';
import 'chartjs-adapter-moment'; // Import moment adapter

export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user')),
      metrics: [],
      workouts: [],
      showWorkout: false,
      caloriesChart: null,
      chartData: {
        labels: [], // Dates for X-axis
        datasets: [
          {
            label: 'Calories Burned',
            data: [], // Calories for Y-axis
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            pointRadius: 5, // Size of the dots
            pointBackgroundColor: 'rgba(75, 192, 192, 1)', // Color of the dots
            fill: true,
            tension: 0.1,
          },
        ],
      },
    };
  },
  methods: {
    calsPerWorkout(date) {
      let calsBurned = 0;
      this.metrics.forEach(metric => {
        if (metric.date === date) {
          calsBurned += Math.round(
            (4.25 * 3.5 * (metric.weight * 0.453592)) / 200 * (metric.sets * metric.reps)
          );
        }
      });
      return calsBurned;
    },
    getMetrics() {
      WorkoutMetricsService.getUsersWorkoutMetrics()
        .then(response => {
          this.metrics = response.data;
          console.log('Fetched metrics:', this.metrics); // Debugging
          this.renderCaloriesChart(); // Render chart after metrics are fetched
        })
        .catch(error => {
          console.error(error);
        });
    },
    selectMetric(metric) {
      WorkoutService.getWorkoutByDate(metric.date)
        .then(response => {
          this.workouts = response.data;
          this.showWorkout = true;
        })
        .catch(error => {
          console.error(error);
        });
    },
    formatDuration(durationInMinutes) {
      const hours = Math.floor(durationInMinutes / 60);
      const minutes = durationInMinutes % 60;
      return `${hours}h ${minutes}m`;
    },
    renderCaloriesChart() {
      const dates = this.groupedMetrics.map((metric) => new Date(metric.date)); // Convert date to Date object
      const calories = this.groupedMetrics.map((metric) => this.calsPerWorkout(metric.date));

      console.log('Dates for chart:', dates); // Debugging
      console.log('Calories for chart:', calories); // Debugging

      if (this.caloriesChart) {
        this.caloriesChart.destroy();
      }

      const ctx = document.getElementById('caloriesChart').getContext('2d');

      // Register all necessary components
      Chart.register(...registerables, TimeScale, LinearScale);

      this.caloriesChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: dates, // X-axis values
          datasets: [
            {
              label: 'Calories Burned',
              data: dates.map((date, index) => ({ x: date, y: calories[index] })), // Map dates to chart data
              borderColor: 'rgba(75, 192, 192, 1)',
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              fill: true,
              tension: 0.1,
            },
          ],
        },
        options: {
          responsive: true,
          scales: {
            x: {
              type: 'time', // Use time scale for X-axis
              time: {
                unit: 'day', // Adjust based on your date range
                tooltipFormat: 'll', // Format for tooltips
              },
              title: {
                display: true,
                text: 'Date',
              },
            },
            y: {
              title: {
                display: true,
                text: 'Calories',
              },
            },
          },
        },
      });
    },
  },
  computed: {
    totalCalsExpended() {
      return Math.round(
        this.metrics.reduce((acc, workout) => {
          return acc + ((4.25 * 3.5 * (workout.weight * 0.453592)) / 200) * (workout.sets * workout.reps);
        }, 0)
      );
    },
    totalLoadVolume() {
      return this.metrics.reduce((acc, workout) => {
        return acc + workout.sets * workout.reps * workout.weight;
      }, 0);
    },
    totalDuration() {
      let totalDuration = 0;
      let previousDate = null;

      this.metrics.forEach(metric => {
        if (metric.date !== previousDate) {
          totalDuration += metric.durationInMinutes;
          previousDate = metric.date;
        }
      });

      return totalDuration;
    },
    groupedMetrics() {
      const uniqueDates = {};

      this.metrics.forEach(metric => {
        if (!uniqueDates[metric.date]) {
          uniqueDates[metric.date] = metric;
        }
      });

      return Object.values(uniqueDates);
    }
  },
  created() {
    this.getMetrics();
  }
};
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
  
  h2,
  h3 {
    text-align: center;
    margin-bottom: 10px;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  th,
  td {
    padding: 12px;
    border: 1px solid #ddd;
    text-align: center;
  }
  
  th {
    background-color: #2c3e50;
    color: #fff;
  }
  
  .odd-row {
    background-color: #f0f0f0;
  }
  
  .metric-row:hover {
    background-color: #ecf0f1;
    cursor: pointer;
  }
  
  .workout-details-table,
  .stats-table {
    margin-top: 20px;
  }
  
  .workout-details-table th,
  .stats-table th {
    background-color: #3498db;
    color: #fff;
  }
  
  .workout-details-table td,
  .stats-table td {
    background-color: #ecf0f1;
  }
  </style>