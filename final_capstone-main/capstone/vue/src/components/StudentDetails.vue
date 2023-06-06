<template>
  <div>
      <h3 class="mt-0">Student Details</h3>
      <ul>
          <li><strong>Name:</strong> {{student.fullname}}</li>
          <li><strong>Email:</strong> {{student.username}}</li>
      </ul>
      <button @click="removeStudent" class="delete red small"><img class="icon invert" src="../assets/delete.svg"> Remove Student</button>
  </div>
</template>

<script>
import courseService from '../services/CourseService.js'
export default {
    props: [ 'student' ],
    methods: {
        removeStudent() {
            courseService.removeStudent(this.$route.params.courseId, this.student.id).then(response => {
                if (confirm("Do you really want to remove this student from the course?")) {
                    if (response.status === 204) {
                        this.students.pop(this.student);
                        this.selectedStudent = '';
                        this.removeSuccessMsg = "Student successfully removed from course.";
                    } else {
                        this.removeErrorMsg = "There was an error removing the student.";
                    }
                }
            });
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