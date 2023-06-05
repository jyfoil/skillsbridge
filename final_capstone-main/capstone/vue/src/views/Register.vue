<template>
  <div id="register" class="text-center">
    <div id="heading-bg" class="center">
      <h1>Register New Account</h1>
    </div>
    <main>
    <form class="form-register flex-column" @submit.prevent="register">
      <!--<h2 class="h3 mb-3 font-weight-normal">Create Account</h2>-->
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div>
      <input
        type="text"
        id="first-name"
        class="form-control"
        placeholder="First Name"
        v-model="user.firstName"
        required
        autofocus
      />
      </div>
      <div>
      <input
        type="text"
        id="last-name"
        class="form-control"
        placeholder="Last Name"
        v-model="user.lastName"
        required
        autofocus
      />
      </div>
      <div>
      <input
        type="text"
        id="email"
        class="form-control"
        placeholder="Email (will serve as your username)"
        v-model="user.username"
        required
        autofocus
      />
      </div>
      <div>
    
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      </div>
      <div>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      </div>
      
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <br />
      <router-link :to="{ name: 'login' }" class="sign-up">Already have an account?</router-link>
    </form>
    </main>
  </div>
</template>

<script>
import authService from '../services/AuthService';
//import LoginHeader from '../components/LoginHeader.vue'

export default {
  name: 'register',
  components: {
    //LoginHeader,
  },
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
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
    console.log("created");
    if (this.$store.state.token != '') {
      console.log("not empty token");
      this.navigateToDashboard();
    }
  }
};
</script>

<style></style>
