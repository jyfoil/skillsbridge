<template>
  <header id="header">
    <router-link :to="{ name: 'home' }" class="header-link">
      <div class="logo-container">
        <img src="../assets/elearning.png" alt="Logo" class="logo">
        <h3 class="portal-title">{{ msg }}</h3>
      </div>
    </router-link>
    

    <div id="nav" v-if="$store.state.token != ''">
      <router-link :to="{ name: 'my-courses' }" class="header-link">My Courses</router-link>
      <div id="notifications" class="icon alert show">
        <img src="../assets/notifications.svg" />
        <div id="notification-tray">
          <ul>
            <li>Notification #1</li>
            <li>Notification #2</li>
          </ul>
        </div>
      </div>
      <router-link :to="{ name: 'logout' }" >Logout</router-link>
    </div>
    <div id="nav" v-else>
      <router-link v-bind:to="{ name: 'login' }" > Login</router-link>
      <router-link v-bind:to="{ name: 'register' }" >Register</router-link>
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
  padding: 10px;
}

header .portal-title {
  flex-grow: 1;
}

#notification-tray {
  display: none;
  position: absolute;
}

#notifications.show #notification-tray {
  display: block;
  right: 0;
  min-width: 200px;
  border: 1px solid #CCC;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background: white;
  z-index: 100;
}

header a {
  color: #429CB9;
  text-decoration: none;
  font-weight: bold;
}

header a:hover {
  color: #195468;
}
</style>
