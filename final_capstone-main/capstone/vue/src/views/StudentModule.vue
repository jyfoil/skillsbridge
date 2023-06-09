<template>
  <div class="dashboard">
    <div id="heading-bg">
      <h1 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h1>
      <router-link class="return-button" tag="button" :to="{ name: 'student-course', params: { courseId: module.courseId }}">
        <img class="icon invert" src="../assets/arrow_back.svg" /> Back to Course
      </router-link>
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
            <span @click="gridView = false" :class="{ bold: !gridView }">List View</span> | <span @click="gridView=true" :class="{ bold: gridView }">Grid View</span>
          </div>
        </div>

        <section id="lessons" :class="{ grid: gridView }">
          <div v-for="lesson in lessons" :key="lesson.id" class="lesson-link">
            <router-link class="lesson-listing flex flex-between" :to="{ name: 'student-lesson', params: { courseId: $route.params.courseId, moduleId: $route.params.moduleId, lessonId: lesson.id } }">
              <div class="mName">{{ lesson.title }}</div><div class="duedate small" v-show="lesson.dueDate != ''">Due Date:{{lesson.dueDate}}</div>
            </router-link>
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
    </main>
  </div>
  
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'

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
      assignments: [],
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
    lessonService.getUpcomingLessons(this.$route.params.courseId).then(response => {
      if (response.status === 200) {
        this.assignments = response.data;
      }
    });
  },
};
</script>



<style scoped>


.lesson-link {
  margin-bottom: 0.5rem;
}

.underline {
  text-decoration: none;
}

.utilities.small {
  margin-top: 0.5rem;
}

.lesson-listing {
  align-items:center;
}

 .grid {
        display:flex;
        gap:20px;
  }

  .grid .lesson-listing {
        flex-basis:33%;
        padding:2.5rem 3rem;
  }

  .grid .duedate {
    display:none;
  }

  .extended-results {
        padding-top:12px;
        margin-top:12px;
        align-items:center;
    }

  .mName {
    font-weight:bold;
  }

@media screen and (max-width: 800px)  {
    #course-stats {
      flex-direction:column;
      text-align:right;
      height:137px;
    }
    .score {
      border-right:none;
      border-bottom:1px solid rgba(255,255,255,0.4);
      padding-right:0;
      margin-right:0;
      padding-bottom:10px;
      margin-bottom:10px;
    }
    button.return-button {
      padding: 0.5rem;
    }
  }

  </style>
