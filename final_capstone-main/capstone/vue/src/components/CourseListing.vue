<template>
  <div class="course-listing">
    <router-link :to="getRoute(course)" class="course-title">{{ course.name }}</router-link>
  </div>
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
  transition: all 200ms;
  cursor: pointer;
  padding: 1rem;
  border: 1px solid #CCC;
  margin-bottom: 0.25rem;
  width: 6rem;
  height: 6rem;
  border-radius: 50%; 
  background: white;
  border-left: 8px solid #429cb9;
  text-align: center;
  color: #444;
  display: flex;
  justify-content: center;
  align-items: center;
}

.course-listing:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transform: scale(1.05);
  border-left-color: #17b0e1;
}

.course-title {
  font-weight: bold;
}

.course-listing a {
  text-decoration: none;
  color: #243e46;
}
</style>
