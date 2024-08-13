<template>
  <main>
    <div class="title-nav">
      <div class="logo">
        <img src="image1.png" alt="logo" id="logo">
        <h1 id="title">FitBuddy</h1>
      </div>
      <div id="nav">
        <router-link v-bind:to="{ name: 'home' }" class="button-link">
          <i class="fas fa-home"></i> Home
        </router-link>&nbsp; &nbsp;
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''" class="button-link">
          <i class="fas fa-sign-out-alt"></i> Logout
        </router-link>
      </div>
    </div>
    <div class="container-login100">
      <div id="capstone-app">
        <router-view />
      </div>
    </div>
    <footer>
      <p>Developed By Miles Nowicki, Nikolas Knappen, Tim Malloy, Zakk Bonham</p>
    </footer>
  </main>
</template>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
}

main {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.container-login100 {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  padding: 15px;
  background-color: #F7FBFC;
}

.title-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #F7FBFC;
  padding: 1rem;
  width: 100%;
}

.logo {
  display: flex;
  align-items: center;
}

#title {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 2.5rem;
  font-weight: bold;
  color: #134B70;
}

#nav {
  display: flex;
  align-items: center;
}

#capstone-app > img {
  display: flex;
  justify-content: center;
  border-radius: 10px;
}

.button-link {
  display: inline-block;
  padding: 8px 12px;
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

#logo {
  width: 100px;
  height: 100px;
}

footer {
  background-color: #134B70;
  color: white;
  text-align: center;
  padding: 1rem;
  width: 100%;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin-top: auto;
}

footer p {
  margin: 0;
}

@media (max-width: 600px) {
  .title-nav {
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  #title {
    font-size: 1.5rem;
  }

  #logo {
    width: 60px;
    height: 60px;
  }

  #nav {
    justify-content: flex-end;
  }
}
</style>
