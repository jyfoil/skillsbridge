<template>
  <div class="home">
    <header-nav msg="Welcome to Unnamed eLearning Platform" />
    <div id="hero">
        <div class="content"><h1>Home</h1></div>
        <div class="sidebar">
          <div class="loginbox" v-if="$store.state.token === ''">
              <form @submit.prevent="login" class="flex-column">
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
          <router-link v-if="$store.state.token === ''" tag="button" :to="{ name: 'Student Home' }">View Available Courses</router-link>
          <div v-else-if="$store.state.user.authorities[0].name === 'ROLE_USER'"><router-link tag="button" :to="{ name: 'Student Home' }">View My Courses</router-link></div>
          <div v-else-if="$store.state.user.authorities[0].name === 'ROLE_ADMIN'"><router-link tag="button" :to="{ name: 'Teacher Home' }">View My Courses</router-link></div>
        </div>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import HeaderNav from '../components/HeaderNav.vue'
export default {
  name: "home",
  components: {
    HeaderNav
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
            if (this.$store.state.user.authorities[0].name == 'ROLE_USER') {
              this.$router.push({name: 'Student Home'});
            }
            else if (this.$store.state.user.authorities[0].name == 'ROLE_ADMIN') {
              this.$router.push({name: 'Teacher Home'});
            }
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

<style scoped>
  #hero { background-image: url('../assets/homephoto.jpg'); height:520px; background-size:75%; background-repeat:no-repeat; }
  #hero .sidebar { width: 25%; padding:20px; box-sizing:border-box; background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%); height: 100%; display:flex; flex-direction: column; gap:12px; justify-content: center; min-width:280px; }
  #hero .sidebar button { background:white; color:#429CB9; }
  .sidebar .loginbox { padding: 20px; background: rgba(255,255,255,0.8); border-radius:0.25rem; box-shadow:0 0 10px rgba(0,0,0,0.2); }
  @media only screen and (max-width: 1040px) {
      #hero {
          background-size:contain;
      }
  }

</style>