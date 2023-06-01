<template>
  <div id="app">
    <header id="header">
        <div class="logo-container">
          <img src="logo.png" alt="Logo" class="logo">
        </div>
        <div id="nav">
          <router-link :to="{ name: 'home' }" class="header-link">
            <h1 class="portal-title">Welcome to Unnamed eLearning Platform</h1>
          </router-link>
        </div>
      
    </header>
    <div id="nav">
      <!-- <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp; -->
      <!-- <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> -->
    </div>
    <router-view />
  </div>
</template>
