<template>
    <div>
        <h2>Exercise Details</h2>
    </div>
    <div>
        <exercise-details />
    </div>
</template>
<script>
import ExerciseDetails from '../components/ExerciseDetails.vue';

export default {
    components: {
        ExerciseDetails
    }
}

</script>

<style scoped>
h2 {
    text-align: center;
    margin: 20px;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;

}
</style>