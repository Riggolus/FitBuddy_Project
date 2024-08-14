<template>
    <select v-model="selectedMonth" @change="fetchEquipmentUsage">
        <option value="current">Current Month</option>
        <option v-for="month in months" :key="month.value" :value="month.value">{{ month.name }}</option>
    </select>
    <div id="table-container">
        <table id="equipment-usage">
            <thead>
                <tr>
                    <th>Kettlebell Weights</th>
                    <th>Usage</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in reducedEquipment" :key="item.equipmentName">
                    <td id="weight">{{ item.weight }}</td>
                    <td id="count">{{ item.usageCount }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
import EquipmentUsageService from '../services/EquipmentUsageService';

export default {
    data() {
        return {
            equipment: [],
            reducedEquipment: [],
            selectedMonth: 'current',
            months: []
        };
    },
    methods: {
        getCurrentEquipmentUsage() {
            EquipmentUsageService.getEquipmentUsageCurrentMonth()
                .then(response => {
                    this.equipment = response.data;
                    this.getReducedEquipmentUsage();
                })
                .catch(e => {
                    console.log(e);
                });
        },
        fetchEquipmentUsage() {
            if (this.selectedMonth === 'current') {
                this.getCurrentEquipmentUsage();
            } else {
                EquipmentUsageService.getEquipmentUsage(this.selectedMonth)
                    .then(response => {
                        console.log(this.selectedMonth);
                        this.equipment = response.data;
                        this.getReducedEquipmentUsage();
                    })
                    .catch(e => {
                        console.log(e);
                    });
            }
        },
        getReducedEquipmentUsage() {
            const usageMap = {};
            this.equipment.forEach(item => {
                if (usageMap[item.weight]) {
                    usageMap[item.weight] += item.usageCount;
                } else {
                    usageMap[item.weight] = item.usageCount;
                }
            });
            this.reducedEquipment = Object.keys(usageMap).map(weight => ({
                weight: weight,
                usageCount: usageMap[weight]
            }));
        },
        generateMonths() {
            const currentYear = new Date().getFullYear();
            const monthNames = [
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ];
            this.months = monthNames.map((name, index) => {
                const monthValue = new Date(currentYear, index, 1).toISOString().split('T')[0];
                return { name, value: monthValue };
            });
        }
    },
    created() {
        this.getCurrentEquipmentUsage();
        this.generateMonths();
    }
}
</script>

<style scoped>
template {
    display: block;
    font-family: Arial, sans-serif;
    margin: 20px;
}

select {
    width: 33vw;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
}

table {
    width: 33vw;
    border-collapse: collapse;
    margin-top: 20px;
}

th {
    background-color: #2c3e50;
    color: white;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 10px;
    text-align: center;
    font-weight: bold;
}

#count {
    padding: 10px;
    text-align: center;
}

#weight {
    padding: 10px;
    border-right: 1px solid #ddd;
    text-align: center;
}

tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

tbody tr {
    transition: background-color 0.3s;
}
</style>