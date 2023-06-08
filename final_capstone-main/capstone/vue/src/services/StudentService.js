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
    sendStudentRequest(body) {
        const header = {'Authorization': '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' };
        const url = 'https://api.sendgrid.com/v3/mail/send';
        return axios.post(url, body, header);
    }
  }