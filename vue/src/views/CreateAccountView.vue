<template>
    test
    <create-account-form-vue v-bind:account="account"></create-account-form-vue>
</template>
<script>
import CreateAccountFormVue from '../components/CreateAccountForm.vue';

export default {
    components: {
        CreateAccountFormVue
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