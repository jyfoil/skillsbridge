<template>
  <div>
    <h1>Lesson Detail</h1>
    <div>
      <h2>Daily Instruction</h2>
      <p>{{ lesson.instruction }}</p>
    </div>
    <div>
      <h2>Resources</h2>
      <p>{{ lesson.resources }}</p>
    </div>
    <div>
      <h2>Homework Assignments</h2>
      <p>{{ lesson.homework }}</p>
    </div>
  </div>
</template>

<script>
import lessonService from "../services/LessonService";

export default {
  data() {
    return {
      lesson: {}
    };
  },
  created() {
    lessonService.getLesson(this.$route.params.id)
      .then(response => {
        this.lesson = response.data;
      })
      .catch(error => {
        console.error(error);
      });
  }
};
</script>
// try something 