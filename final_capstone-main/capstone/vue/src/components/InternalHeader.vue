<template>
  <header id="header">
    <!-- Logo and portal title -->
    <div class="header-left">
    <router-link :to="{ name: 'home' }" class="header-link">
      <div class="logo-container">
        <img src="../assets/SkillsBridge.png" alt="Logo" class="logo">
        <h3 class="portal-title">{{ msg }}</h3>
      </div>
    </router-link>
    </div>

    <!-- Right navigation -->
    <div id="nav" class="right-nav">
      <!-- Commenting out the notification bell -->
      <!--
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
      -->
      
      <div class="header-right">
         <router-link :to="myCoursesLink" class="header-link">
          My Courses
        </router-link>
        <span class="separator"> |</span>
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

    computed: {
    myCoursesLink() {
      if (this.$store.state.user.authorities[0].name === 'ROLE_TEACHER') {
        return { name: '/teacher-home' };
      } else if (this.$store.state.user.authorities[0].name === 'ROLE_STUDENT') {
        return { name: '/student-home' };
      } else {
        return { name: 'home' };
      }
    },
  },
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
