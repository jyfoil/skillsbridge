<template>
  <div id="login" class="text-center">
    <div id="heading-bg" class="center">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    </div>
    <main>
    <form class="form-signin flex-column" @submit.prevent="login">
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <div>
      <label for="username" class="sr-only">Username</label>
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
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      /></div>
      <button type="submit">Sign in</button><br />
      <router-link :to="{ name: 'register' }">Sign Up</router-link>
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
            this.$router.push("/");
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
    }
  }
};
</script>
<style>
@import url('../assets/styles.css');
  .form-signin, .form-register {
    width:100%;
    max-width: 400px;
    padding:20px;
    background:#F5F5F5;
    border:1px solid #D8D8D8;
    box-shadow:0 3px 15px rgba(0,0,0,0.1);
    border-radius:4px;
    margin:0 auto;
  }
</style>
