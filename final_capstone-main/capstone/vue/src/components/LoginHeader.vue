<template>
  <header id="header">
    <div class="header-left">
      <router-link :to="{ name: 'home' }" class="header-link">
        <div class="logo-container">
          <img src="../assets/elearning.png" alt="Logo" class="logo">
          <h3 class="portal-title">{{ msg }}</h3>
        </div>
      </router-link>
    </div>
    <div class="header-right">
      <router-link :to="{ name: 'login' }">Login</router-link>
      <span class="separator"> |</span>
      <router-link :to="{ name: 'register' }">Register</router-link>
    </div>
  </header>
</template>

<script>
export default {
  props: ['msg'],
};
</script>

<style scoped>
header {
  display: flex;
  align-items: center;
  padding: 0px 10px 0px 10px;
}

.header-left {
  flex-grow: 1;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo {
  width: 50px;
  height: 50px;
}

.portal-title {
  margin-left: 10px;
  color: #444 ;
  font-weight: bold;
}

.header-right {
  display: flex;
  align-items: center;
}

header a {
  color: #429CB9;
  text-decoration: none;
  font-weight: bold;
  margin-left: 10px;
  margin-right: 10px;
}

header a:hover {
  color: #195468;
}

.separator {
  margin: 0 5px;
  color: #429CB9;
  font-weight: bold;
}

</style>
