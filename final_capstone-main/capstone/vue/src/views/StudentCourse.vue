<template>
  <div class="dashboard">
    <div id="heading-bg" class="p-relative tall">
      <h1 class="dashboard-title"><span class="hide-mobile">{{ $store.state.user.fullname }} - </span><span class="course-name">{{ course.name }}</span></h1>
      <router-link class="return-button" tag="button" :to="{ name: 'Student Home'}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back <span class="hide-mobile">to Dashboard</span></router-link>
      <div id="course-stats" class="p-absolute flex">
        <div class="score">
          <div>Grade</div>
          <div class="course-grade">{{grade*10}}%</div>
        </div>
        <div class="completed">{{submissions.length}} of {{numAssignments}}<br />assignments<br />submitted</div>
      </div>
    </div>
    <main id="dashboard-content">
      <div id="content">
        <h2 class="underline">Course Description</h2>
        <section id="description">
          <p>{{course.description}}</p>
        </section>
        <div class="p-relative"><h2 class="underline">Modules</h2><div class="utilities small"><span @click="gridView = false" :class="{ bold: !gridView}">List View</span> | <span @click="gridView=true" :class="{ bold: gridView}">Grid View</span></div></div>
        <section id="modules" :class="{ grid: gridView}">
          <module-listing v-for="module in modules" :key="module.id" :module="module" />
        </section>
        <h2 class="underline">Submissions</h2>



        <div class="submission-listing flex flex-column" v-for="submission in submissions" :key="submission.submittedAt">
            <div class="flex flex-between">
                <img class="icon" src="../assets/assignment.svg" />
                <div class="capsule grade" v-if="submission.grade != 0">Grade: {{ submission.grade }}/10</div>
                <div class="capsule dark" v-else>Not Graded</div>
                <div class="small flex-grow text-right">Submitted at: <span class="light">{{submission.submittedAt}}</span></div><button class="small" v-show="showSubmission != submission.submissionId" @click="showSubmission = submission.submissionId">View</button><button v-show="showSubmission === submission.submissionId" class="small muted" @click="showSubmission = 0">Hide</button></div>
            <div class="submission-content" v-show="showSubmission === submission.submissionId">
                <div v-html="submission.content"></div>
            </div>
        </div>



      </div>
      <section>
        <h3 class="underline">Upcoming Assignments</h3>
        <div>
          <div class="mb-1" v-for="assignment in assignments" :key="assignment.id">
            Lesson: {{assignment.title}}<br />
            <span class="small">Due on {{assignment.dueDate}}</span>
          </div>
        </div>
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
import studentService from '../services/StudentService.js'
import submissionService from '../services/SubmissionService.js'
import ModuleListing from '../components/ModuleListing.vue'
export default {
    data() {
        return {
            modules: [],
            assignments: [],
            showSubmission:0,
            course: {
                courseId: this.$route.params.courseId,
                name: '',
                description: ''
            },
            successMsg: '',
            numAssignments:0,
            errorMsg: '',
            newModule: {
                courseId: this.$route.params.courseId,
            },
            gridView: false,
            grade: 0,
            submissions: [],
        }
    },
    created: function() {
        courseService.getCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.course = response.data;
            }
        }),
        moduleService.getModules(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.modules = response.data;
            }
        }),
        lessonService.getUpcomingLessons(this.$route.params.courseId).then(response => {
          if (response.status === 200) {
              this.assignments = response.data;
          }
        }),
        lessonService.getNumberOfAssignmentsInCourse(this.$route.params.courseId).then(response => {
        if (response.status === 200) {
              this.numAssignments = response.data;
          }
        }),
        studentService.getStudentGrade(this.$route.params.courseId, this.$store.state.user.id).then(response => {
          if (response.status === 200) {
            this.grade = response.data;
          }
        }),
        submissionService.getSubmissionsByCourseAndStudent(this.$route.params.courseId, this.$store.state.user.id).then(response => {
          if (response.status === 200) {
            this.submissions = response.data;
          }
        })
    },
    components: {
        ModuleListing
    },
    
}
</script>

<style scoped>
    select.form-control {
        padding: inherit;
        outline:0;
        border-bottom-left-radius: 0;
        border-bottom-right-radius: 0;
    }
    
    .icon.search-icon {
        position:absolute;
        right:4px;
        top:3px;
        opacity: 0.5;
    }
    #description textarea {
        min-height:150px;
        resize:none;
    }
    #description textarea:disabled {
        padding:0;
        border:none;
        background:white;
    }
    .extended-results {
        padding-top:12px;
        margin-top:12px;
        align-items:center;
    }
    .grid {
        display:flex;
        gap:12px;
    }
    .grid .module-listing {
        flex-basis:33%;
        padding:2rem 1rem;
    }

   .contact-teacher-wrapper {
    display: flex;
    justify-content: center; 
    align-items: flex-start; 
    height: 100%;
    margin-top: 1rem;
  }


  .contact-teacher-button {
    padding: 0.75rem 1.5rem;
    font-size: 1rem;
    background-color: #429cb9;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  .contact-teacher-button:hover {
    background-color: #17b0e1;
  }

  #course-stats {
    right:0;
    top:0;
    bottom:0;
    margin:auto 0;
    padding:10px 30px;
    background:rgba(0,0,0,0.5);
    border-top-left-radius:8px;
    border-bottom-left-radius:8px;
    gap:0;
    height:57px;
  }
  
   
  .course-grade {
    font-weight:bold;
    font-size:2rem; 
  }

  .score {
    border-right: 1px solid rgba(255,255,255,0.4);
    padding-right: 15px;
    margin-right: 15px;
  }

  #heading-bg h1 {
    margin-right: 202px;
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