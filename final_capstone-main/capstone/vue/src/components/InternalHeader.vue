<template>
  <header id="internal-header">
    <!-- Logo and portal title -->
    <router-link :to="{ name: 'home' }" class="header-link">
      <div class="logo-container">
        <img src="../assets/elearning.png" alt="Logo" class="logo">
        <h3 class="portal-title">{{ msg }}</h3>
      </div>
    </router-link>
    

    <!-- Right navigation -->
    <div id="nav" class="right-nav">
      <div class="header-item">
        <div id="notifications" class="icon notify" @click="toggleNotificationTray" :class="{ show: isNotificationTrayVisible }">
          <img src="../assets/notifications.svg"  />
          <div id="notification-tray" :class="{ active: isNotificationTrayVisible }" tabindex="-1">
            <ul>
              <li>Notification #1</li>
              <li>Notification #2</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="header-item">
        <router-link :to="{ name: 'my-courses' }" class="header-link">
          My Courses
        </router-link>
      </div>
      <div class="header-item">
        <router-link :to="{ name: 'logout' }">Logout</router-link>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      isNotificationTrayVisible: false,
    };
  },
  props: ['msg'],
  methods: {
    toggleNotificationTray() {
      this.isNotificationTrayVisible = !this.isNotificationTrayVisible;
    },
    hideNotificationTray() {
      this.isNotificationTrayVisible = false;
    },
  },
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

#notifications.show #notification-tray,
#notifications.show #notification-tray.active {
  display: block;
  right: 0;
  min-width: 200px;
  border: 1px solid #CCC;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background: white;
  z-index: 100;
}

.right-nav {
  margin-left: auto;
}

.header-item {
  margin-left: 10px;
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
