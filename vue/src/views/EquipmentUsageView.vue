<template>
    <div id="main">
        <div id="secondary">
            <h2>Equipment Usage</h2>
            <equipment-use/>
        </div>
    </div>
    
</template>
<script>
import EquipmentUse from '../components/EquipmentUse.vue';

export default{
    components: {
        EquipmentUse
    },
    data() {
        return {
            equipment: 
                {
                    equipmentName: 'Kettlebell',
                    usage: 0
                },
        }
    }
}

</script>

<style scoped>
/* Styling for the main container */

#main {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

#secondary {
    margin: 20px;
    padding: 20px;
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 50%;
}

/* Styling for the heading */
h2 {
    display: flex;
    justify-content: center;
    font-size: 24px;
    color: #134B70;
    border-bottom: 2px solid #185884;
    padding-bottom: 10px;
    margin-bottom: 20px;
    font-weight: 600;
}
</style>