<template>
    <div class="container">
        <div class="table-container">
            <h2>Metrics</h2>
            <h3>Workout History</h3>
            <table>
                <thead>
                    <tr>
                        <th>Date of Workout</th>
                        <th>Calories</th>
                        <th>Duration</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(metric, index) in groupedMetrics" :key="index" @click="selectMetric(metric)">
                        <td>{{ metric.date }}</td>
                        <td>{{ calsPerWorkout(metric.date) }}cals</td>
                        <td>{{ formatDuration(metric.durationInMinutes) }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div v-show="showWorkout" class="table-container">
            <h3>Workout Details</h3>
            <table class="workout-table">
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
                    <tr v-for="workout in workouts" :key="workout.exerciseName">
                        <td>{{ workout.exerciseName }}</td>
                        <td>{{ workout.sets }}</td>
                        <td>{{ workout.reps }}</td>
                        <td>{{ workout.weight }}</td>
                        <td>{{ workout.duration }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table-container">
            <h3>Stats</h3>
            <table>
                <thead>
                    <tr>
                        <th>Total Calories Burned</th>
                        <th>Total Load Volume</th>
                        <th>Total Duration</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{ totalCalsExpended }}cals</td>
                        <td>{{ totalLoadVolume }}lbs</td>
                        <td>{{ formatDuration(totalDuration) }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>


<script>
import WorkoutMetricsService from '../services/WorkoutMetricsService';
import WorkoutService from '../services/WorkoutService';

export default {
    data(){
        return{
            
            userId: this.$route.params.id,
            metrics: [


            ],
            date: '',
            duration: 0,
            statsOn: true,
            workouts: [
                {
                    workout: {
                        workoutId: 0,
                        exerciseId: 0,
                        sets: 0,
                        reps: 0,
                        weight: 0,
                        duration: 0,
                        exerciseName: '',
                    }
                }
            ],
            exercise: {
                exerciseName: ''
            },
            showWorkout: false
            
        };
    },
    methods: {
        calsPerWorkout(date) {
           
           let firstWorkout = date;
           let calsBurned = 0;
           for (let i = 0; i < this.metrics.length; i++){
                   if (this.metrics[i].date === firstWorkout)
                   calsBurned += Math.round((4.25 * 3.5 * (this.metrics[i].weight * 0.453592) / 200) * (this.metrics[i].sets * this.metrics[i].reps))
                   
                }
               return calsBurned;
           
       },
        getMetrics(){
            WorkoutMetricsService. getMemberMetrics(this.$route.params.id)
                .then((response) => {
                    this.metrics = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        selectMetric(metric) {
            WorkoutService.getUserWorkoutByDate(metric.date, this.userId)
                .then((response) => {
                    this.workouts = response.data;
                    console.log(this.workouts);
                    this.showWorkout = true;

                })
                .catch((error) => {
                    console.log(error);
                });
            console.log(metric.date);
        },
        formatDuration(durationInMinutes){
            let hours = Math.floor(durationInMinutes / 60);
            let minutes = durationInMinutes % 60;
            return `${hours}h ${minutes}m`;
        }
    
    },
    computed: {
        totalCalsExpended() {
            return Math.round(
                this.metrics.reduce((acc, workout) => {
                    console.log(workout);
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
            if (this.metrics.length === 0) return 0;

            let totalDuration = 0;
            let previousDate = null;

            for (let i = 0; i < this.metrics.length; i++) {
                if (this.metrics[i].date !== previousDate) {
                    totalDuration += this.metrics[i].durationInMinutes;
                    previousDate = this.metrics[i].date;
                }
        }

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
    created(){
        this.getMetrics();
        console.log(this.$route.params.id);
        console.log(this.metrics);
    }
}

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

.container {
  width: 100%;
  min-height: 75vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.table-container {
  width: 100%;
  max-width: 900px;
  padding: 30px 20px;
  border-radius: 10px;
  background-color: white;
  margin-bottom: 20px;
}

h2, h3 {
  color: black;
  margin-bottom: 20px;
  font-weight: 600;
}

table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
}

thead {
  background-color: #f4f4f4;
}

thead th {
  text-align: left;
  font-size: 16px;
  font-weight: 600;
  padding: 10px;
  border-bottom: 2px solid #ccc;
}

tbody tr {
  transition: all 0.2s ease-in-out;
}

tbody td {
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

tbody tr:hover {
  background-color: #e0e0e0;
  cursor: pointer;
}

.table-container .workout-table th, .table-container .workout-table td {
  text-align: center;
}

.table-container .workout-table .exercise-item {
  background-color: #f8f8f8;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease-in-out;
}

.table-container .workout-table .exercise-item:hover {
  background-color: #e0e0e0;
  transform: scale(1.05);
}

</style>
