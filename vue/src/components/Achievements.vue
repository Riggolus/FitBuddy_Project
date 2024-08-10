<template>
    <div>
      <input type="text" v-model="goalDescription" placeholder="Filter by goal description">
      <input type="checkbox" v-model="completed"> Show only completed goals
  
      <table>
        <thead>
          <tr>
            <th>Goal</th>
            <th>Completed</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="achievement in filteredGoals" :key="achievement.goalId">
            <td>{{ achievement.goalDescription }}</td>
            <td>
              <button v-if="!achievement.completed" @click="completeGoal(achievement)">Complete</button>
              <span v-else>Completed</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  
    <form v-on:submit.prevent="createGoal">
      <label>Goal Description</label>
      <input type="text" v-model="editAchievement.goalDescription">
      <button type="submit" class="button-link">Submit</button>
    </form>
  </template>
  
  <script>
  import AchievementsService from '../services/AchievementsService';
  import confetti from 'canvas-confetti'; // Import the confetti library
  
  export default {
    data() {
      return {
        achievements: [],
        achievement: {
          goalId: '',
          userId: this.$store.userId,
          goalDescription: '',
          completed: ''
        },
        editAchievement: {
          userId: this.$store.userId,
          goalDescription: '',
          completed: false
        },
        goalDescription: '',
        completed: ''
      };
    },
    computed: {
      filteredGoals() {
        let filteredAchievements = this.achievements;
        if (this.goalDescription) {
          filteredAchievements = filteredAchievements.filter((achievement) =>
            achievement.goalDescription.toLowerCase().includes(this.goalDescription)
          );
        }
        if (this.completed) {
          filteredAchievements = filteredAchievements.filter(
            (achievement) => achievement.completed === this.completed
          );
        }
        return filteredAchievements;
      }
    },
    methods: {
      getAchievements() {
        AchievementsService.viewMyAchievements()
          .then((response) => {
            this.achievements = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      },
      createGoal() {
        AchievementsService.createAchievements(this.editAchievement)
          .then(() => {
            console.log("Achievement Created");
            this.getAchievements(); // Refresh the list after creation
          })
          .catch((error) => {
            console.log(error);
          });
      },
      completeGoal(achievement) {
        achievement.completed = true;
        AchievementsService.updateAchievement(achievement)
          .then(() => {
            this.showConfetti(); // Trigger confetti animation
            alert(`Congratulations on completing your goal: ${achievement.goalDescription}!`);
            this.getAchievements(); // Refresh the list after completion
          })
          .catch((error) => {
            console.log(error);
          });
      },
      showConfetti() {
        confetti({
          particleCount: 150,
          spread: 60,
          origin: { y: 0.6 }
        });
      }
    },
    created() {
      this.getAchievements();
    }
  };
  </script>