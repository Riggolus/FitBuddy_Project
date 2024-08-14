<template>
    <create-account-form-vue v-bind:account="account" id="create-account"></create-account-form-vue>
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
        };
    },
}
</script>

<style scoped>
#create-account {
    background: #B9D7EA;
    border: #D6E6F2 solid 5px;
    border-radius: 10px;
    overflow: hidden;
    padding: 33px 55px 33px 55px;
    box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #134B70;
}
</style>