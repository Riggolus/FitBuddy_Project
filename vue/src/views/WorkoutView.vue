<template>
    <button id="check-out" v-on:click="checkOut" class="button-link">Complete Workout/check-out</button>
    <current-workout :stats-on="statsOn" id="current-workout" />
    <div id="exercise-container">
        <h2>Select an Exercise</h2>
        <div class="input-container">
            <label for="exerciseName">Search: </label>
            <input type="text" id="exerciseName" v-model="exerciseName" placeholder="Enter exercise name">
        </div>
        <div class="exercise-list">
            <div v-for="exercise in filteredExercises" :key="exercise.exerciseId" class="exercise-item">
                <span>{{ exercise.exerciseName }}</span>
                <button v-on:click="selectExercise(exercise.exerciseId)" class="button-link">View Exercise</button>
            </div>
        </div>
    </div>


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
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
    display: flex;
    flex-flow: column wrap;
    height: 100 vh;
}

h2 {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

}

.button-link:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.button-link:active {
    background-color: #00408d;
    border-color: #00408d;
}

#exercise-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

#check-out {
    margin: 10px;

    display: block;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 30px;
}

.input-container {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
}

.exercise-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.exercise-item {
    flex: 1 1 calc(50% - 10px);
    box-sizing: border-box;
    border: 1px solid #ddd;
    padding: 10px;
    background-color: #f9f9f9;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.button-link {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 8px 16px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    border-radius: 4px;
    cursor: pointer;
}

.button-link:hover {
    background-color: #0056b3;
}
</style>