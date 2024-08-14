<template>
    <div id="details">
        <h2>{{ exercise.exerciseName }}</h2>
        <img :src="getGifSrc(id)" alt="exercise.exerciseName" width="200" height="200">
        <p>{{ exercise.exerciseDescription }}</p>
        <add-exercise-form v-bind:workout="workout" id="exercise-form" />
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
                sets: null,
                reps: null,
                weight: null,
                duration: null
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
    max-width: 800px;
    margin: 0 auto;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    padding: 2rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h2 {
    color: #134B70;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin-bottom: 1rem;
    font-size: 2rem;
}

img {
    border-radius: 8px;
    border: 2px solid #007bff;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
    margin-bottom: 1rem;
    object-fit: cover;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

img:hover {
    transform: scale(1.1);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4);
    z-index: 20;
}

p {
    color: #666;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    line-height: 1.6;
    margin: 1rem 0;
}

#exercise-form {
    margin-top: 2rem;
}
</style>
