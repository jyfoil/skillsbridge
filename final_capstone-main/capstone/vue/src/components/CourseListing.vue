<template>
    <router-link :to="getRoute(course)" class="course-title course-listing">{{ course.name }}</router-link>
</template>

<script>
export default {
  props: ['course'],
  methods: {
    getRoute(course) {
      if (this.$store.state.user.authorities[0].name === 'ROLE_ADMIN') {
        // Teacher route
        return { name: 'teacher-course', params: { courseId: course.courseId } };
      } else {
        // Student route
        return { name: 'student-course', params: { courseId: course.courseId } };
      }
    },
  },
};
</script>


<style scoped>
.course-listing {
  display: flex;
  align-items: center;
  border: 1px solid #CCC;
  padding: 0.75rem 1rem;
  border-radius: 6px;
  margin-bottom: 1rem;
  border-left: 7px solid #429cb9;
  text-decoration: none;
  color: #243e46;
  width: 10rem;
  height: 5rem;
}

.course-listing:hover {
  border-color: #666;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  transform: scale(1.05);
  border-left-color: #17b0e1;
}

.course-title {
  font-weight: bold;
  color: #666;
}

.course-listing a {
  text-decoration: none;
  color: #243e46;
}
</style>
