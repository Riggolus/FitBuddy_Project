<template>
    user metrics
    <div>
        <h2>Your Metrics</h2>
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
                    <td>{{ metric.date }} </td>
                    <td>{{ calsPerWorkout(metric.date) }}cals</td>
                    <td>{{ formatDuration(metric.durationInMinutes) }}</td>
                </tr>
            </tbody>
        </table>
        <div v-show="showWorkout">
            <table>
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
                        <td>{{workout.exerciseName }}</td>
                        <td>{{workout.sets}}</td>
                        <td>{{workout.reps}}</td>
                        <td>{{workout.weight}}</td>
                        <td>{{workout.duration}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div>
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
import ExerciseServices from '../services/ExerciseServices';

export default{
    data(){
        return{
            user: JSON.parse(localStorage.getItem('user')),
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
            WorkoutMetricsService.getUsersWorkoutMetrics()
                .then((response) => {
                    this.metrics = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        selectMetric(metric) {
            WorkoutService.getWorkoutByDate(metric.date)
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
        // calsPerWorkout(this.metric.date) {
           
        //     let firstWorkout = this.metric.date;
        //     let calsBurned = 0;
        //     for (let i = 0; i < this.metrics.length; i++){
        //             if (this.metrics[i].date === firstWorkout)
        //             calsBurned += Math.round(4.25 * 3.5 * (this.metrics[i].weight * 0.453592) / 200) * (this.metrics[i].sets * this.metrics[i].reps)
                    
            
        //         }
        //         return calsBurned;
            
            
        // },
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
            const totalCalsPerDay = {};

            this.metrics.forEach(metric => {
                // getting total calories here
                // totalCalsPerDay[metric.date] = (Math.round(
                //     ((4.25 * 3.5 * (metric.weight * 0.453592)) / 200) * (metric.sets * metric.reps)));
                if (!uniqueDates[metric.date]) {
                    uniqueDates[metric.date] = metric;
                }
            });

            return Object.values(uniqueDates, totalCalsPerDay);
        }
    
    }, 
    created(){
        this.getMetrics();
    }
}
</script>