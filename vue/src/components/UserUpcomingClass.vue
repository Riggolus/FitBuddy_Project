<template>
    <div class="upcoming-class-container">
        <h2>Your Upcoming Classes</h2>
        <div v-for="upComingClass in upComingClasses" :key="upComingClass.id" class="class-card">
            <p class="class-name">{{ upComingClass.className }}</p>
            <p class="class-date">{{ formatDate(upComingClass.sessionDate) }}</p>
        </div>
    </div>
</template>

<script>
import ClassRegistrationService from '../services/ClassRegistrationService';

export default {
    data() {
        return {
            upComingClasses: [],
        }
    },
    methods: {
        getUpcomingClasses() {
            ClassRegistrationService.getRegisteredClasses()
                .then((response) => {
                    this.upComingClasses = response.data;
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        formatDate(dateString) {
            const options = { month: 'long', day: 'numeric' };
            return new Date(dateString).toLocaleDateString(undefined, options);
        }
    },
    mounted() {
        this.getUpcomingClasses();
    }
};
</script>

<style scoped>
.upcoming-class-container {
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
    padding: 10px;
    background-color: #f0f4f8;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.upcoming-class-container h2 {
    font-size: 1.2rem;
    margin-bottom: 15px;
    color: #134B70;
    text-align: center;
}

.class-card {
    background-color: white;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 5px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.class-card:hover {
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
}

.class-name {
    font-size: 1rem;
    font-weight: bold;
    color: #007bff;
    margin-bottom: 5px;
}

.class-date {
    font-size: 0.9rem;
    color: #555;
}
</style>
