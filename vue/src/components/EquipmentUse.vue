<template>
    <select v-model="selectedMonth" @change="fetchEquipmentUsage">
        <option value="current">Current Month</option>
        <option v-for="month in months" :key="month.value" :value="month.value">{{ month.name }}</option>        
    </select>
    <table>
        <thead>
            <tr>
                <th>Kettlebell Weight</th>
                <th>Usage</th>        
            </tr>
        </thead>
        <tbody>
            <tr  v-for="item in reducedEquipment" :key="item.equipmentName">
                <td>{{ item.weight }}</td>
                <td>{{ item.usageCount }}</td>
                            
            </tr>
        </tbody>
    </table>
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