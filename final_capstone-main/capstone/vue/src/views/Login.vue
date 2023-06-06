<template>
  <div id="login" class="text-center">
    <div id="heading-bg" class="center">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    </div>
    <main>
    <form class="form-signin flex-column" @submit.prevent="login">
      <div
        class="alert alert-danger"
        role="alert" @click="invalidCredentials = false"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <div>
      <!-- <label for="username" class="sr-only">Username</label> -->
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      /></div>
      <div>
      <!-- <label for="password" class="sr-only">Password</label> -->
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      /></div>
      <button type="submit">Sign in</button><br />
      <router-link :to="{ name: 'register' }" class="sign-up">Not enrolled? Sign up here!</router-link>
    </form>
    </main>
    <!--<button class="view-courses-button" @click="navigateToCourses">View Courses</button>-->
   
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.navigateToDashboard();
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    navigateToCourses() {
      this.$router.push("/course-list");
    },
    navigateToDashboard() {
      if (this.$store.state.user.authorities[0].name == 'ROLE_USER') {
        this.$router.push({name: 'Student Home'});
      }
      else if (this.$store.state.user.authorities[0].name == 'ROLE_ADMIN') {
        this.$router.push({name: 'Teacher Home'});
      }
    }
  },
  created: function() {
    if (this.$store.state.token != '') {
      console.log("not empty token");
      this.navigateToDashboard();
    }
  }
};
</script>
<style>
@import url('../assets/styles.css');

</style>
<style scoped>
  .alert-success::after {
    display:none;
  }
</style>