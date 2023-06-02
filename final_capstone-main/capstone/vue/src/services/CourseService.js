import axios from 'axios';

export default {
    getCoursesForUser() {
      return axios.get('/courses');
    }
  }