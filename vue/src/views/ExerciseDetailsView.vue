<template>
    Details 
    <h2>{{exercise.exerciseName}}</h2>
    <!-- <img :src="getGifSrc(exercise.exerciseId)" alt="exercise.exerciseName" width="200" height="200">  -->
    <p>{{exercise.exerciseDescription}}</p> 

    <add-exercise-form v-bind:workout="workout"/>
</template>
<script>
import AddExerciseForm from '../components/AddExerciseForm.vue';
import ExerciseServices from '../services/ExerciseServices';

export default {
    components: {
        AddExerciseForm
    },
    data() {
        return {
            exercise: {
            },
            workout: {
                exerciseId: this.$route.params.id,
                sets: 0,
                reps: 0,
                weight: 0,
                duration: 0
            }
        };
    },
    methods: {
        getCurrentExercise() {
            ExerciseServices.getExercise(this.$route.params.id)
                .then((response) => {
                    this.exercise = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        // getGifSrc(exerciseId) {
        //     return require(`../assets/exerciseGifs/${exerciseId}.gif`);
        // }
    },
    mounted() {
        this.getCurrentExercise();
    }
}
</script>