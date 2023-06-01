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
<script>
  export default {
    components: {

    }
  };
</script>

<style>
  #app { max-width: 1280px; margin:0 auto;}
  .icon {width: 25px; }
  .icon img { width: 100%;}
  #hero { padding:20px; background-color: #0093E9; background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%); display:flex; }
  #hero .content { flex-grow:1; }
  #hero .content h1, #hero .content h2, #hero .content h3 { color: white; font-weight:bold; }
</style>