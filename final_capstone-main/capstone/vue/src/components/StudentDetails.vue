<template>
  <div>
      <h3 class="mt-0">Student Details</h3>
      <ul>
          <li><strong>Name:</strong> {{student.fullname}}</li>
          <li><strong>Email:</strong> {{student.username}}</li>
      </ul>
  </div>
</template>

<script>
export default {
    props: [ 'student' ],
    methods: {

    }
}
</script>

<style scoped>
    ul { list-style: none; padding-left:0;}


    button.red .icon {
        margin-left:-6px;
    }


</style>