<template>
    <div id="details">
        <h2>{{exercise.exerciseName}}</h2>
        <img :src="getGifSrc(id)" alt="exercise.exerciseName" width="200" height="200"> 
        <p>{{exercise.exerciseDescription}}</p> 

        <add-exercise-form v-bind:workout="workout" id="exercise-form"/>
    </div>
    
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
            id: this.$route.params.id,
            workout: {
                exerciseId: this.$route.params.id,
                sets: 0,
                reps: 0,
                weight: 0,
                duration: 0
            },
            
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
        getGifSrc(id) {
        return new URL(`../assets/exerciseGifs/${id}.gif`, import.meta.url).href;
    }
    },
    mounted() {
        this.getCurrentExercise();
    }
}
</script>

<style scoped>
#details {
    display: grid;
    grid-template-areas: 
        "h2 exercise-form"
        "img exercise-form"
        "p exercise-form";
    grid-template-columns: 50% 50%;
    width: 1170px;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    overflow: hidden;
    /* padding: 77px 55px 33px 55px; */
    padding: 55px;
    box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}

h2 {
    grid-area: h2;
    text-align: center;
}

img {
    grid-area: img;
    margin: auto;
    border-radius: 10px;
    box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
}

p {
    grid-area: p;
    text-align: center;
}

exercise-form {
    grid-area: exercise-form;
    /* display: flex; */
    /* flex-direction: column; */
}
</style>