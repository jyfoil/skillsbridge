<template>
  <div class="dashboard">
    <div id="heading-bg">
      <h1 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h1>
      <router-link class="return-button" tag="button" :to="{ name: 'student-course', params: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Course</router-link>
    </div>
    <main id="dashboard-content">
      <div id="content">
        <div> 
          <h2 class="underline">Module Description:</h2>
          <p>{{ module.description }}</p>
        </div>
        <div class="p-relative">
          <h2 class="underline">Lessons</h2>
          <div class="utilities small">
            <span @click="gridView = false" :class="{ bold: !gridView }">List View</span> | <span @click="gridView = true" :class="{ bold: gridView }">Grid View</span>
          </div>
        </div>
        <section id="lessons" :class="{ grid: gridView }">
          <div v-for="lesson in lessons" :key="lesson.id" class="lesson-link">
            <h4>
              <router-link :to="{ name: 'student-lesson', params: { courseId: $route.params.courseId, moduleId: $route.params.moduleId, lessonId: lesson.id } }">{{ lesson.title }}</router-link>
              <LessonListing v-for="lesson in lessons" :key="lesson.id" :lesson="lesson" />
            </h4>
          </div>
        </section>
        

      </div>
      <section>
        <h3 class="assignments">Upcoming Assignments</h3>
        <div class="contact-teacher-wrapper">
          <button class="contact-teacher-button">Contact Teacher</button>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'
import LessonListing from '../components/LessonListing.vue'

export default {
  data() {
    return {
      lessons: [],
      hideAddLessonForm: true,
      course: {},
      module: {
        id: this.$route.params.moduleId,
        courseId: this.$route.params.id,
        name: '',
        description: ''
      },
      successMsg: '',
      errorMsg: '',
      gridView: false,
    }
  },
  created() {
    courseService.getCourse(this.$route.params.courseId).then(response => {
      if (response.status === 200) {
        this.course = response.data;
      }
    });

    moduleService.getModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
      if (response.status === 200) {
        this.module = response.data;
      }
    });

    lessonService.getLessonsByModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
      if (response.status === 200) {
        this.lessons = response.data;
      }
    });
  },
  components: {
    LessonListing
  },
};
</script>

<style scoped>
/* ... your existing styles ... */

.lesson-link {
  margin-bottom: 0.5rem;
}

.underline {
  text-decoration: underline;
}

.utilities.small {
  margin-top: 0.5rem;
}

.grid {
  display: flex
}
