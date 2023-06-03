<template>
  <div>
    <h1>Submit Assignment</h1>
    <form @submit.prevent="submitAssignment">
      <div>
        <label for="assignment">Assignment</label>
        <textarea id="assignment" v-model="assignment.submission" required></textarea>
      </div>
      <button type="submit">Submit Assignment</button>
    </form>
  </div>
</template>

<script>
import assignmentService from "../services/AssignmentService";

export default {
  data() {
    return {
      assignment: {
        submission: ""
      }
    };
  },
  methods: {
    submitAssignment() {
      assignmentService.submitAssignment(this.assignment)
        .then(response => {
          this.$router.push("/assignment-submitted");
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>