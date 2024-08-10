<template>
    <div>
        <h3>Average Time Per Workout: {{ formatDuration(this.averageTotalMinutesSpent) }}</h3>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                    <th>Date of Workout</th>
                    <th>Duration</th>
                    
                </tr>
            </thead>
            <tbody>
                <tr v-for="(metric, index) in metrics" :key="index">
                    <td>{{ metric.dateVisitedGym }} </td>
                    <td>{{ formatDuration(metric.totalMinutes) }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
import WorkoutMetricsService from '../services/WorkoutMetricsService';

export default {
    data(){
        return{
            userId: this.$route.params.id,
            metrics: [
            ],
            averageTotalMinutesSpent: 0,
            
            
        }
    },
    methods: {
        getMetrics(){
            WorkoutMetricsService.getMemberMetricsForAllSessions()
            .then((response) => {
                this.metrics = response.data;
                this.getTotalAverageMinutesSpent();
            })
            .catch((error) => {
                console.log(error);
            });
        }, 
        getTotalAverageMinutesSpent(){
            let avgTotalMinutes = 0;
            for (let i = 0; i < this.metrics.length; i++){
                console.log("this.metrics[i].totalMinutes: ", this.metrics[i].totalMinutes);
                console.log("this.metrics[i]: ", this.metrics[i]);
                avgTotalMinutes += this.metrics[i].totalMinutes;
            }
            this.averageTotalMinutesSpent = avgTotalMinutes / this.metrics.length;
        },
        formatDuration(durationInMinutes){
            let hours = Math.floor(durationInMinutes / 60);
            let minutes = Math.floor(durationInMinutes % 60);
            return `${hours}h ${minutes}m`;
        }
    }, created(){
        this.getMetrics();
    }
}
</script>