<template>
  <div>
      <h3 class="mt-0">Student Details</h3>
      <ul>
          <li><strong>Name:</strong> {{student.fullname}}</li>
          <li><strong>Email:</strong> {{student.username}}</li>
          <li><strong>Progress:</strong> {{submissions.length}} of {{numAssignments}} Assignments Completed</li>
      </ul>
      <div class="capsule grade">Current grade: {{grade*10}}%</div>
  </div>
</template>

<script>
import studentService from '../services/StudentService.js'
import lessonService from '../services/LessonService.js'
export default {
    data() {
        return {
            grade: 0,
            numAssignments: 0,
        }
    },
    props: [ 'student', 'submissions' ],
    methods: {

    },
    watch: {
        student: function() {
            studentService.getStudentGrade(this.$route.params.courseId, this.student.id).then(response => {
                if (response.status === 200) {
                    this.grade = response.data;
                }
            });
            lessonService.getNumberOfAssignmentsInCourse(this.$route.params.courseId).then(response => {
                if (response.status === 200) {
                    this.numAssignments = response.data;
                }
            })
        },
    }
}
</script>

<style scoped>
    ul { list-style: none; padding-left:0;}

    .capsule {
        display:inline-block;
        margin-top:0.5rem;
    }

    button.red .icon {
        margin-left:-6px;
    }
    #students li {
        margin-bottom:0.4rem;
    }
    #students li strong {
        width: 79px;
        display: inline-block;
    }
</style>