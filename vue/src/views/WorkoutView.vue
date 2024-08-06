<template>
    <h2>Workout</h2>
    <div></div>
    <button id="check-out" v-on:click="checkOut">Complete Workout/check-out</button>
</template>
<script>
import CheckInCheckOutService from '../services/CheckInCheckOutService';
export default {
    data() {
        return {
            user: JSON.parse(localStorage.getItem('user'))
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
        }
    }
}
</script>