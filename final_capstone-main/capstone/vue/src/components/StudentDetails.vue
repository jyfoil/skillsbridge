<template>
  <div>
      <h3 class="mt-0">Student Details</h3>
      <ul>
          <li><strong>Name:</strong> {{student.fullname}}</li>
          <li><strong>Email:</strong> {{student.username}}</li>
      </ul>
      <div>Progress: {{submissions.length}} of x Assignments Completed</div>
      <div>Current grade: {{grade*10}}%</div>
  </div>
</template>

<script>
import studentService from '../services/StudentService.js'
export default {
    data() {
        return {
            grade: 0,
        }
    },
    props: [ 'student', 'submissions' ],
    methods: {

    },
    created: function() {
        studentService.getStudentGrade(this.$route.params.courseId, this.student.id).then(response => {
            if (response.status === 200) {
                this.grade = response.data;
            }
        })
    },
    watch: {
        student: function() {
            studentService.getStudentGrade(this.$route.params.courseId, this.student.id).then(response => {
                if (response.status === 200) {
                    this.grade = response.data;
                }
            })
        },
    }
}
</script>

<style scoped>
    ul { list-style: none; padding-left:0;}


    button.red .icon {
        margin-left:-6px;
    }


</style>