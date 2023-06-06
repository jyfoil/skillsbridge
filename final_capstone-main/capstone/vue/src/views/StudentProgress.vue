<template>
  <div>
    <h2>Course Progress</h2>
    <div v-for="module in modules" :key="module.id">
      <h3>{{module.title}}</h3>
      <div v-for="lesson in module.lessons" :key="lesson.id">
        <div>{{lesson.title}}</div>
        <div>{{lesson.completed ? 'Completed' : 'Not Completed'}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import ProgressService from '../services/ProgressService.js';
import ModuleService from '../services/ModuleService.js';
import LessonService from '../services/LessonService.js';

export default {
  data() {
    return {
      modules: [],
    };
  },
  async mounted() {
    const userId = /* Retrieve user ID here */;
    const courseId = /* Retrieve course ID here */;
    
    const modules = await ModuleService.getModules(courseId);
    const completedLessons = await ProgressService.getCompletedLessonsForUser(userId);
    
    for (let module of modules.data) {
      const lessons = await LessonService.getLessonsByModule(courseId, module.id);
      for (let lesson of lessons.data) {
        lesson.completed = completedLessons.data.includes(lesson.id);
      }
      module.lessons = lessons.data;
    }
    this.modules = modules.data;
  },
};
</script>