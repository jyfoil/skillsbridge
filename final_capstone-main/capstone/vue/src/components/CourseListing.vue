<template>
  <div class="course-listing">
      <router-link :to="{name:'teacher-course', params: { id: course.courseId }}"><div class="course-title">{{course.name}}</div></router-link>
  </div>
</template>

<script>
export default {
    props: [ 'course' ],
}
</script>

<style scoped>
    .course-listing { transition:all 200ms; cursor:pointer; padding:1rem; border:1px solid #CCC; margin-bottom:0.25rem; flex-basis:200px; border-radius:6px; background:white; border-left:8px solid #429cb9; height:4rem; text-align:center; color:#444; }
    .course-listing:hover {  box-shadow:0 0 10px rgba(0,0,0,0.1); transform:scale(1.05); border-left-color:#17b0e1; }
    .course-title { font-weight:bold; }
</style>