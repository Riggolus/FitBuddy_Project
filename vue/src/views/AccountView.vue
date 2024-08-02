<template>
    <edit-account-form v-bind:account="account"></edit-account-form>
   
</template>

<script>
import EditAccountForm from '../components/EditAccountForm.vue';

export default {

    components: {
        EditAccountForm
    },

    data() {
        return {
            account: {
                firstName: '',
                lastName: '',
                email: '',
                photo: '',
                profile: '',
                goal: ''
            }
            // registrationErrors: false,
            // registrationErrorMsg: 'There were problems submitting the info.'
        };
    },
}
</script>