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
        <main id="dashboard-content">
            <div id="content">
                <div> 
                    <h2 class="underline">Module Description:</h2>
                    <p>{{ module.description }}</p>
                </div>

                <h2 class="underline">Lessons</h2>
                <section id="lessons" :class="{ grid: gridView }">
                    <span @click="gridView = false" :class="{ bold: !gridView}">List View</span> | <span @click="gridView=true" :class="{ bold: gridView}">Grid View</span>
                    <div v-for="lesson in lessons" :key="lesson.id">
                        <h4>
                            <router-link :to="{ name: 'student-lesson', params: { courseId:$route.params.courseId, moduleId:$route.params.moduleId, lessonId:lesson.id } }">{{ lesson.title }}
                            </router-link>
                        </h4>
                    </div>
                </section>
            </div>
            
            <section>
            <h3 class="assignments">Upcoming Assignments</h3>
            <div>
            <div class="mb-1" v-for="assignment in assignments" :key="assignment.id">
                Lesson: {{assignment.title}}<br />
                <span class="small">Due on {{assignment.dueDate}}</span>
            </div>
            </div>
            <div class="contact-teacher-wrapper">
                <a class="contact-teacher-button" href="mailto:teacher1@test.edu">Contact Teacher</a>
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
<<<<<<< HEAD
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
=======
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
            assignments: [],
            successMsg: '',
            errorMsg: '',
        }
    },
    created: function() {
        courseService.getCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.course = response.data;
            }
        }),
        moduleService.getModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.module = response.data;
            }
        }),
        lessonService.getLessonsByModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.lessons = response.data;
            }
        }),
        lessonService.getUpcomingLessons(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.assignments = response.data;
            }
        })
    },
    components: {
        
    },
    
}
>>>>>>> main
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
