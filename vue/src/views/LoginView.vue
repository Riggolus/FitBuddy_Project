<template>
  <div id="login">
    <img src="src\assets\imgs\istockphoto-1448303096-612x612.jpg" alt="workout" />
    <form v-on:submit.prevent="login" class="login100-form">
      <h1>Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit" id="sign-in">Sign in</button>
      <p>
        <router-link v-bind:to="{ name: 'register' }" class="button-link">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
#login {
  display: grid;
  grid-template-areas:
    "img login-form";
  grid-template-columns: 50% 50%;
  width: 1170px;
  background: #B9D7EA;
  border: #D6E6F2 solid 5px;
  border-radius: 10px;
  overflow: hidden;
  padding: 77px 55px 33px 55px;
  box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
}

.login100-form {
  grid-area: login-form;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.login100-form>h1 {
  text-align: center;
}

p {
  text-align: center;
}

#login>img {
  grid-area: img;
  margin: auto;
  border-radius: 10px;
  box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);

}

.button-link {
  display: inline-block;
  padding: 5px;
  background-color: #007bff;
  color: white;
  text-align: center;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s, border-color 0.3s;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

}

.button-link:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}

.button-link:active {
  background-color: #00408d;
  border-color: #00408d;
}

h1 {
  margin-bottom: 2rem;
  color: #134B70;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;


}

.form-input-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #134B70;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

button {
  padding: 0.75rem;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056B3;
}

p {
  margin-top: 1rem;
}

.alert {
  padding: 0.75rem;
  margin-bottom: 1rem;
  border: 1px solid transparent;
  border-radius: 4px;
  text-align: center;
}

.alert-danger {
  background-color: #F8D7DA;
  border-color: #F5C6CB;
  color: #721C24;
}

.alert-success {
  background-color: #D4EDDA;
  border-color: #C3E6CB;
  color: #155724;
}
</style>