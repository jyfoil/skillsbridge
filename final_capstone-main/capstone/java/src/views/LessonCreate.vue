<template>
  <div>
    <h1>Create Lesson</h1>
    <form @submit.prevent="createLesson">
      <div>
        <label for="instruction">Daily Instruction</label>
        <textarea id="instruction" v-model="lesson.instruction" required></textarea>
      </div>
      <div>
        <label for="resources">Resources (links)</label>
        <textarea id="resources" v-model="lesson.resources"></textarea>
      </div>
      <div>
        <label for="homework">Homework Assignments</label>
        <textarea id="homework" v-model="lesson.homework"></textarea>
      </div>
      <button type="submit">Create Lesson</button>
    </form>
  </div>
</template>

<script>
import lessonService from "../services/LessonService";

export default {
  data() {
    return {
      lesson: {
        instruction: "",
        resources: "",
        homework: ""
      }
    };
  },
  methods: {
    createLesson() {
      lessonService.createLesson(this.lesson)
        .then(response => {
          this.$router.push(`/lesson/${response.data.id}`);
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>