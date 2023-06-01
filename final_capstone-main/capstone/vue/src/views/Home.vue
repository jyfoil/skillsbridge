<template>
  <div class="home">
    <header-nav msg="Welcome to Unnamed eLearning Platform" />
    <div id="hero">
        <div class="content"><h1>Home</h1></div>
        <div>Login Box</div>
    </div>
    <p>You must be authenticated to see this</p>
  </div>
</template>

<script>
import HeaderNav from '../components/HeaderNav.vue'
export default {
  name: "home",
  components: {
    HeaderNav
  }
};
</script>
