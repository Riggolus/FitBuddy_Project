<template>
    This will hold analytics
    <div>
        <h2 v-show="isNavFromHome">Your last workout:</h2>
        <h2 v-show="!isNavFromHome">Great Work! Here's your workout report:</h2>
    </div>
    <div>
        <current-workout/>
    </div>
    

</template>
<script>
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
            statsOn: true,
            isNavFromHome: false
        };
    }, methods: {  
        getCaloriesBurned() {
                for (let exercise in this.exercises) {
                    this.caloriesBurned += 4.25 * 3.5 * 60;
                    
                
            }
        }
    }
}
</script>