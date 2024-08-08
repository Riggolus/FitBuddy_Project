<template> 
    <div>
        <table id="currentWorkout">
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
                    <td>{{workout.sets}}</td>
                    <td>{{workout.reps}}</td>
                    <td>{{workout.weight}}</td>
                    <td>{{workout.duration}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <table v-show="statsOn">
        <thead>
            <tr>
                <th>Calories Burned</th>
                <th>Load Volume</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>{{ estimatedCalsExpended }}cals</td>
                <td>{{ loadVolume }}lbs</td>
            </tr>
        </tbody>
    </table>
</template>
<script>
import WorkoutService from '../services/WorkoutService';
import ExerciseServices from '../services/ExerciseServices';

export default {
    props: {
        statsOn: {
            type: Boolean,
            default: true
        }
    },
    data() {
        return {
            workouts: [
                {
                   
                    workoutId: 0,
                    exerciseId: 0,
                    sets: 0,
                    reps: 0,
                    weight: 0,
                    duration: 0,
                    exerciseName: '',
                }
            ],
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
        
        console.log("Current Date: ", this.currentDate);
        console.log("Workouts: ", this.workouts);
        // this.getExerciseName();
        
    }
}
</script>