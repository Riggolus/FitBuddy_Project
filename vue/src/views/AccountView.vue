<template>
    <h1>Create Account</h1>
    <form action="">
        <label for="first-name">First Name</label>
        <input type="text" id="first-name"/>
        <label for="last-name">Last Name</label>
        <input type="text" id="last-name"/>
        <div>    
            <label for="email">Email</label>
            <input type="email" id="email"/>
        </div>
        <div>
            <label for="photo">Photo</label>
            <input type="file" id="photo"/>
        </div>
        <div>
            <label for="profile">Profile</label>
            <textarea type="textbox" id="profile">
            </textarea>
        </div>
        <div>
            <!-- Potentially add a list of goals the user can check off as completed  -->
            <label for="goal">Goals</label>
            <textarea type="textbox" id="goal">
            </textarea>
        </div>

    </form>
</template>

<script>
import AccountService from '../services/AccountService';

export default {
    
}
</script>