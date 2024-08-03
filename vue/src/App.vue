<template>
  <body>
    <div id="capstone-app">
      <h1 id="title">Workout Companion</h1>
      <div id="nav">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>
      <router-view />
    </div>
  </body>
</template>

<style>
body {
  display: grid;
  grid-template-areas: 'h1 h1 h1 .'
                       'nav nav nav .'
                       'router-view router-view router-view .';
}



</style>