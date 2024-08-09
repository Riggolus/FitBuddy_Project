<template>
  <main>
    <div class="title-nav">
      <h1 id="title">Workout Companion</h1>
        <div id="nav">
          <router-link v-bind:to="{ name: 'home' }" class="button-link">Home</router-link>&nbsp; &nbsp;
          <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''" class="button-link">Logout</router-link>
        </div>
    </div>
    <div class="container-login100">
      <div id="capstone-app">
          <router-view />
      </div>
    </div>
    
  </main>
</template>

<style scoped>

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
</style>