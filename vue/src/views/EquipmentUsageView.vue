<template>
    <div>
        <h2>Equipment Usage</h2>
        <equipment-use/>
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