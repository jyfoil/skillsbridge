<template>
  <div class="home">
    <div id="hero">
        <div class="content"><h1>The Bridge to your Future</h1></div>
        <div class="sidebar">
          <div class="loginbox" v-if="$store.state.token === ''">
              <form @submit.prevent="login" class="flex-column">
                <div
                  class="alert alert-danger"
                  role="alert"
                  v-if="invalidCredentials"
                >Invalid username and password!</div>
                <div>
                  <label for="username" class="sr-only">Username</label>
                  <input
                    type="text"
                    id="username"
                    class="form-control"
                    placeholder="Username (Email)"
                    v-model="user.username"
                    required
                  />
                </div>
                <div>
                  <label for="password" class="sr-only">Password</label>
                  <input
                    type="password"
                    id="password"
                    class="form-control"
                    placeholder="Password"
                    v-model="user.password"
                    required
                  />
                </div>
                <button type="submit">Sign in</button>
              </form>
          </div>
          <!--<router-link v-if="$store.state.token === ''" tag="button" :to="{ name: 'Student Home' }">View Available Courses</router-link>-->
          <div v-else-if="$store.state.user.authorities[0].name === 'ROLE_USER'"><h4 class="center">Hello, {{ $store.state.user.firstname }}</h4><router-link tag="button" :to="{ name: 'Student Home' }">View My Courses</router-link></div>
          <div v-else-if="$store.state.user.authorities[0].name === 'ROLE_ADMIN'"><h4 class="center">Hello, {{ $store.state.user.firstname }}</h4><router-link tag="button" :to="{ name: 'Teacher Home' }">View My Courses</router-link></div>
        </div>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
//import HeaderNav from '../components/LoginHeader.vue'
export default {
  name: "home",
  components: {

  },
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
  }
};
</script>

<style scoped>
  #hero { height:520px; }
  #hero .content h1 { background:rgba(0,0,0,0.6); display:inline-block; padding: 0.5rem 1rem; position:absolute; bottom:0; }
  #hero .content { background-image: url('../assets/althomephoto.jpg'); height:520px; background-size:cover; background-repeat:no-repeat; background-position-y: center; background-position-x:center; box-sizing: border-box; padding:20px; position:relative; }
  .sidebar { width: 25%; padding:20px; box-sizing:border-box; background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%); height: 100%; display:flex; flex-direction: column; gap:12px; justify-content: center; align-items:center; min-width:280px; }
  .sidebar h4 { color:white; }
  .sidebar button { background:white; color:#429CB9; width: 100%; }
  .sidebar .loginbox { padding: 20px; width:100%; box-sizing:border-box; background: rgba(255,255,255,0.8); border-radius:0.25rem; box-shadow:0 0 10px rgba(0,0,0,0.2); }
</style>