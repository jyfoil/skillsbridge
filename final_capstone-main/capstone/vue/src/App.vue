<template>
  <div id="app">
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
  #hero { background-color: #0093E9; background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%); display:flex; align-items:center; }
  #hero .content { flex-grow:1; }
  #hero .content h1, #hero .content h2, #hero .content h3 { color: white; font-weight:bold; }
</style>