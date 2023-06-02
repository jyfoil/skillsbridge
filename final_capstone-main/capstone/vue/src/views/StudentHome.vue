<template>
    <div>
        <div class="dashboard">
            <h1 class="dashboard-title">{{ userFirstName }}'s Dashboard</h1>
        </div>
    </div>
</template>



<script>

export default {
    components: {
        
    },
  computed: {
      userFirstName() {
          const username = this.$store.state.user.username;

          const firstName = username

          return firstName;
      },
  },
};
</script>