<template>
    <div class="dashboard">
        <h1 class="dashboard-title">{{ userFirstName }} Dashboard</h1>
    </div>
</template>

<script>
export default {
  computed: {
      userFirstName() {
          const username = this.$store.state.user.username;

          const firstName = username

          return firstName;
      },
  },
};
</script>