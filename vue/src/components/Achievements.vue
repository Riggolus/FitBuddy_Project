<template>
    <div class="achievements-container">
      <div class="filter-container">
        <input 
          type="text" 
          v-model="goalDescription" 
          placeholder="Filter by goal description" 
          class="input-filter"
        >
        <label class="checkbox-label">
          <input type="checkbox" v-model="completed"> Show only completed goals
        </label>
      </div>
  
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Goal</th>
              <th>Completed</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="achievement in filteredGoals" :key="achievement.goalId" :class="{ 'odd-row': achievement.goalId % 2 !== 0 }">
              <td>{{ achievement.goalDescription }}</td>
              <td>
                <button 
                  v-if="!achievement.completed" 
                  @click="completeGoal(achievement)" 
                  class="complete-button"
                >
                  Complete
                </button>
                <span v-else class="completed-label">Completed</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <form @submit.prevent="createGoal" class="goal-form">
        <label for="goalDescription" class="form-label">Goal Description</label>
        <input 
          type="text" 
          v-model="editAchievement.goalDescription" 
          id="goalDescription" 
          class="input-goal"
        >
        <button type="submit" class="submit-button">Submit</button>
      </form>
    </div>
  </template>
  
  <script>
  import AchievementsService from '../services/AchievementsService';
  import confetti from 'canvas-confetti';
  
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
            this.getAchievements();
          })
          .catch((error) => {
            console.log(error);
          });
      },
      completeGoal(achievement) {
        achievement.completed = true;
        AchievementsService.updateAchievement(achievement)
          .then(() => {
            this.showConfetti();
            alert(`Congratulations on completing your goal: ${achievement.goalDescription}!`);
            this.getAchievements();
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
  
  <style scoped>
  .achievements-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f4f4f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .filter-container {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .input-filter {
    width: 60%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .checkbox-label {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
  }
  
  .table-container {
    margin-top: 20px;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th, td {
    padding: 12px;
    border: 1px solid #ddd;
    text-align: center;
  }
  
  th {
    background-color: #134B70;
    color: #fff;
  }
  
  .odd-row {
    background-color: #f0f0f0;
  }
  
  .complete-button {
    padding: 6px 12px;
    background-color: #134B70;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .complete-button:hover {
    background-color: #0e3a57;
  }
  
  .completed-label {
    font-weight: bold;
    color: green;
  }
  
  .goal-form {
    margin-top: 20px;
  }
  
  .form-label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #134B70;
  }
  
  .input-goal {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-bottom: 12px;
  }
  
  .submit-button {
    padding: 10px 20px;
    background-color: #134B70;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .submit-button:hover {
    background-color: #0e3a57;
  }
  </style>
  