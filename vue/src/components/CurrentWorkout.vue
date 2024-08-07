<template>
    This is your current workout 
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
</template>
<script>
import WorkoutService from '../services/WorkoutService';
import ExerciseServices from '../services/ExerciseServices';

export default {
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
        // getExerciseName() {
            
                
        //     ExerciseServices.getExercise(this.workout.exerciseId)
        //         .then((response) => {
        //             this.exercise = response.data;
        //             this.workout.exerciseName = this.exercise.exerciseName;
        //         })
        //         .catch((error) => {
        //             console.log(error);
        //         });
            
        // }
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