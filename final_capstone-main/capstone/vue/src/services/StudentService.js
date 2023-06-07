import axios from 'axios';

export default {
    getAllStudents() {
      return axios.get('/students');
    },
    getCourseStudents(courseId) {
        return axios.get(`/course/${courseId}/students`);
    },
    getStudentGrade(courseId, studentId) {
        return axios.get(`/course/${courseId}/student/${studentId}/grade`);
    },
  }