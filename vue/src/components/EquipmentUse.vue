<template>
    <select>
        <option value="">Current Month</option>   
        <option value="january">January</option>
        <option value="february">February</option>
        <option value="march">March</option>
        <option value="February">April</option>
        <option value="january">May</option>
        <option value="February">June</option>
        <option value="january">July</option>
        <option value="February">August</option>
        <option value="january">September</option>
        <option value="February">October</option>
        <option value="january">November</option>
        <option value="February">December</option>
    </select>
    <table>
        <thead>
            <tr>
                <th>Kettlebell Weight</th>
                <th>Usage</th>        
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in equipment" :key="item.equipmentName">
                <td>Place holder</td>
                <td>Place holder</td>
                    
                    
            </tr>
        </tbody>
    </table>
</template>
<scrip>
    export default {
        data() {
            return {
                equipment: [
                    {
                        equipmentName: '',
                        usage: 0
                    }
                ]
            }
        }
    }
</scrip>