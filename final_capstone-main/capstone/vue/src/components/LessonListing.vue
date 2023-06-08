
<template>
  <router-link class="lesson-listing" :key="lesson.id" :to="{name:lessonView, params: { courseId: module.courseId, moduleId: module.id, lessonId: lesson.id }}">
    <span class="lesson-title">{{ lesson.name }}</span> 
    <span class="lesson-description">- {{ lesson.description }}</span>
</router-link>
</template>





<script>
export default {
    props: [ 'lesson' ],
    lessonView: 'student-lesson',
    created: function() {
        this.lessonView = (this.$store.state.user.authorities[0].name === "ROLE_ADMIN") ? 'teacher-lesson' : 'student-lesson';
    }
}
</script>


<style scoped>
    .lesson-listing {
        display: block;
        border: 1px solid #CCC;
        padding: 0.75rem 1rem;
        border-radius: 4px;
        margin-bottom: 1rem;
        border-left: 7px solid #429cb9;
        text-decoration:none;
        color:#243e46;
    }
    .lesson-listing:hover {
        border-color:#666;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        border-left-color: #17b0e1;
    }
    .lesson-title {
        font-weight:bold;
    }
    .grid .lesson-description {
        display:none;
    }
    .grid .lesson-listing {
        text-align:center;
    }
</style>
<!--

<script>
export default {
  props: {
    lesson: {
      type: Object,
      required: true
    }
  }
};
</script>




-->