<template>
    <h2>Workout</h2>
    <div>
        <table id="exercises">
            <thead>
                <tr>
                    Exercise
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <input type="text" id="exerciseName" v-model="exerciseName">
                    </td>
                </tr>
                <tr v-for="exercise in filteredExercises" :key="exercise.exerciseId">
                    <td>{{exercise.exerciseName}}</td>
                    <td>
                        <div v-show="selectedExerciseId === exercise.exerciseId">
                            gif goes here
                            {{ exercise.description }}
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <button id="check-out" v-on:click="checkOut">Complete Workout/check-out</button>
</template>
<script>
import CheckInCheckOutService from '../services/CheckInCheckOutService';
import ExerciseServices from '../services/ExerciseServices';
export default {
    data() {
        return {
            user: JSON.parse(localStorage.getItem('user')),
            exercises: [],
            exerciseName: '',
        };
    },
    computed: {
        filteredExercises() {
            let filteredExercises = this.exercises;
            if (this.exerciseName) {
                filteredExercises = filteredExercises.filter((exercise) => exercise.exerciseName
                    .toLowerCase()
                    .includes(this.exerciseName));
            }
            return filteredExercises;
        }
    },
       methods: {
        checkOut() {
            
            console.log("this.user.username: ", this.user.username);
            console.log("Checking out");
            CheckInCheckOutService.checkOut()
                .then(() => {
                    console.log("Checked Out");
                    this.$router.push({ name: 'analytics', params: { username: this.user.username } });
                })
                .catch((error) => {
                    console.log(error);
                });  
        },
        getExercises() {
            ExerciseServices.getExercises()
                .then((response) => {
                    this.exercises = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        selectExercise(exerciseId) {
            // Toggle the selected exercise ID
            this.selectedExerciseId = this.selectedExerciseId === exerciseId ? null : exerciseId;   
        }
    },
    mounted() {
        this.getExercises();
    }

}
</script>