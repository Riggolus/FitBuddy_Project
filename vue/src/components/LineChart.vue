<template>
    <div>
      <line-chart :data="chartData" :options="chartOptions" />
    </div>
  </template>
  
  <script>
  import { Line } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale)
  
  export default {
    name: 'LineChart',
    components: {
      LineChart: Line
    },
    props: {
      chartData: Object,
      chartOptions: Object
    }
  }
  </script>
  