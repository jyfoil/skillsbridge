<template>
  <div class="courseListing">
      {{course.name}}<br />
      {{course.description}}
  </div>
</template>

<script>
export default {
    props: [ 'course' ],
}
</script>

<style>

</style>