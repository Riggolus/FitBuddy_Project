<template>
    Form for adding sets, reps, duration...
    <form v-on:submit.prevent="addExerciseToWorkout">
        <label for="sets">Sets</label>
        <input type="number" id="sets" v-model="editWorkout.sets">
        <label for="reps">Reps</label>
        <input type="number" id="reps" v-model="editWorkout.reps">
        <label for="weight">Weight</label>
        <input type="number" id="weight" v-model="editWorkout.weight">
        <label for="duration">Duration</label>
        <input type="number" id="duration" v-model="editWorkout.duration">
        <button type="submit">Add Exercise</button>
        <button type="reset" v-on:click="cancelExercise">Back to Exercise Selection</button>
    </form>
</template>
<script>
// I'll need to import workout services
import WorkoutService from '../services/WorkoutService';

export default {
    props: {
        workout: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            editWorkout: {
                exerciseId: this.$route.params.id,//this id is based on the param in the url
                sets: 0,
                reps: 0,
                weight: 0,
                duration: 0
            }
        };
    },
    methods: {
        cancelExercise() {
            // I'll need to navigate back to the workout view
            this.$router.go(-1);
        },
        addExerciseToWorkout(editWorkout) {
            WorkoutService.createWorkout(this.editWorkout)
                .then(() => {
                    console.log("Exercise added to workout: " + this.editWorkout);
                    // I'll need to navigate back to the workout view
                    this.$router.go(-1);
                })
                .catch((error) => {
                    console.log(error);
                });

        }
    },
    
}
</script>