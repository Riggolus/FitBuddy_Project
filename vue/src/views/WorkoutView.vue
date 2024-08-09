<template>
    <h2>Workout</h2>
    <current-workout :stats-on="statsOn" id="current-workout"/>
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
                        <button v-on:click="selectExercise(exercise.exerciseId)" class="button-link">View Exercise</button>
                    </td>
                        
                    
                </tr>
            </tbody>
        </table>
    </div>
    <button id="check-out" v-on:click="checkOut" class="button-link">Complete Workout/check-out</button>
    
</template>
<script>
import CheckInCheckOutService from '../services/CheckInCheckOutService';
import ExerciseServices from '../services/ExerciseServices';
import CurrentWorkout from '../components/CurrentWorkout.vue';
export default {
    components: {
        CurrentWorkout
    },
    data() {
        return {
            user: JSON.parse(localStorage.getItem('user')),
            exercises: [],
            selectedExerciseId: null,
            exerciseName: '',
            statsOn: false
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
            // route user to exercise via exerciseId
            console.log("exerciseId: ", exerciseId);
            this.$router.push({ name: 'exerciseDetails', params: { id: exerciseId } });
              
        }
    },
    mounted() {
        this.getExercises();
    }

}
</script>

<style scoped>
#exercises {
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}

h2 {
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}

.button-link {
    display: inline-block; 
    padding: 5px; 
    background-color: #007bff; 
    color: white; 
    text-align: center; 
    text-decoration: none;
    border-radius: 5px; 
    border: 1px solid #007bff; 
    font-weight: bold;
    transition: background-color 0.3s, border-color 0.3s;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

}
.button-link:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.button-link:active {
    background-color: #00408d;
    border-color: #00408d;
}
</style>