<template>
  <main>
    <div class="title-nav">
      <img src="image1.png" alt="logo" id="logo">
      <h1 id="title">FitBuddy</h1>
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
    
  </main>
</template>

<!-- <style scoped>

.container-login100 {
  grid-area: container;
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    padding: 15px;
    background-color: #F7FBFC;
    overflow-x: hidden;
}

.title-nav {
    grid-area: title-nav;
    background-color: #F7FBFC;
    margin-right: auto;
    padding: 1rem;
    
}

#title {
    display: flex;
    justify-content: center;
    align-items: center;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    /* font-size: 2rem; */
    /* font-weight: bold; */
    color: #134B70;
}

#nav {
    display: flex;
    justify-content: center;
}

#capstone-app > img {
    grid-area: img;
    display: flex;
    justify-content: center;
    border-radius: 10px;
}

.button-link {
    display: inline-block; 
    padding: 5px; 
    background-color: #007bff; 
    color: white; 
    text-align: center; 
    text-decoration: none;
    border-radius: 5px; 
    /* border: 1px solid #007bff;  */
    /* font-size: 16px;  */
    /* font-weight: bold; */
    transition: background-color 0.3s, border-color 0.3s;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

}
.button-link:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.button-link:active {
    background-color: #00408d;
    border-color: #00408d;
}
</style> -->

<style scoped>
.container-login100 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  padding: 15px;
  background-color: #F7FBFC;
  overflow-x: hidden;
}

.title-nav {
  display: flex;
  position: relative;
  align-items: center;
  background-color: #F7FBFC;
  padding: 1rem;
  width: 100%;
}

#title {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 2.5rem; /* Increase the size */
  font-weight: bold;
  color: #134B70;
  flex: 1;
}


#nav {
  display: flex;
  justify-content: center; /* Center buttons horizontally */
  position: absolute;
  left: 50%;
  transform: translateX(-50%); /* Center vertically with the title */
}

#capstone-app > img {
  display: flex;
  justify-content: center;
  border-radius: 10px;
}

.button-link {
  display: inline-block;
  padding: 8px 12px; /* Increase padding for better appearance */
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
</style>