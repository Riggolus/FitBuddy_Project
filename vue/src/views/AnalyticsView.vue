<template>
    This will hold analytics
    <div>
        <h2>Your last workout:</h2>
        <h2>Great Work! Here's your workout report:</h2>
    </div>
    <div>
        <current-workout/>
    </div>
    <div>
        <user-metrics/>
    </div>
    

</template>
<script>
import CurrentWorkout from '../components/CurrentWorkout.vue';
import UserMetrics from '../components/UserMetrics.vue';

export default {
       components: {
        CurrentWorkout,
        UserMetrics
    },
    data() {
        return {
            user: JSON.parse(localStorage.getItem('user')),
            exercises: [],
            selectedExerciseId: null,
            exerciseName: '',
            statsOn: true,
            isNavFromHome: false
        };
    }
}
</script>