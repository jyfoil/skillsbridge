<template>
  <header id="header">
    <router-link :to="{ name: 'home' }" class="header-link">
      <div class="logo-container">
        <img src="../assets/elearning.png" alt="Logo" class="logo">
      </div>
    </router-link>
    <h3 class="portal-title">{{ msg }}</h3>
    <div id="nav" v-if="$store.state.token !== ''">
      <router-link
        v-if="$store.state.user.authorities[0].name === 'ROLE_ADMIN'"
        :to="{ name: 'Teacher Home' }"
      >
        My Courses
      </router-link>
      <router-link
        v-if="$store.state.user.authorities[0].name === 'ROLE_USER'"
        :to="{ name: 'Student Home' }"
      >
        My Courses
      </router-link>
      <div
        @click="toggleNotification"
        id="notifications"
        class="icon alert"
        :class="{ show: showNotification }"
      >
        <img src="../assets/notifications.svg" />
        <div id="notification-tray">
          <ul>
            <li>Notification #1</li>
            <li>Notification #2</li>
          </ul>
        </div>
      </div>
      <router-link :to="{ name: 'logout' }">Logout</router-link>
    </div>
    <div id="nav" v-else>
      <router-link :to="{ name: 'login' }">Login</router-link>
      <router-link :to="{ name: 'register' }">Register</router-link>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      showNotification: false,
    };
  },
  props: ['msg'],
  methods: {
    toggleNotification() {
      this.showNotification = !this.showNotification;
    },
  },
  computed: {
    isLoginPage() {
      return (
        this.$route.name === 'login' ||
        this.$route.name === 'register' ||
        !this.$route.name
      );
    },
  },
};
</script>

<style scoped>
header {
  display: flex;
  align-items: center;
  padding: 10px 0px;
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

@media only screen and (max-width: 1280px) {
  header {
    padding: 10px;
  }
}
</style>
