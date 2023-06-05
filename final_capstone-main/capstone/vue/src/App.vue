<template>
  <div id="app">
    <component :is="headerComponent" :msg="headerMsg" />
    <router-view />
  </div>
</template>

<script>
import LoginHeader from '@/components/LoginHeader.vue';
import InternalHeader from '@/components/InternalHeader.vue';

export default {
  components: {
    LoginHeader,
    InternalHeader,
  },
  computed: {
    headerComponent() {
      // Check if the route name is login, logout, or register
      if (
        this.$route.name === 'login' ||
        this.$route.name === 'register' ||
        this.$route.name === 'home'
      ) {
        return LoginHeader;
      } else {
        return InternalHeader;
      }
    },
    headerMsg() {
      return this.$route.name === 'login' || this.$route.name === 'register' || this.$route.name === 'home' ? 'Welcome to SkillsBridge' : 'SkillsBridge';
    },
  },
};
</script>

<style>
#app {
  max-width: 1280px;
  margin: 0 auto;
}

.icon {
  width: 25px;
}

.icon img {
  width: 100%;
}

#hero {
  background-color: #0093e9;
  background-image: linear-gradient(160deg, #0093e9 0%, #80d0c7 100%);
  display: flex;
  align-items: center;
}

#hero .content {
  flex-grow: 1;
}

#hero .content h1,
#hero .content h2,
#hero .content h3 {
  color: white;
  font-weight: bold;
}
</style>
