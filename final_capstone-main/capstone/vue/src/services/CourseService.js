import axios from 'axios';

export default {
    getCoursesForUser() {
      return axios.get('/courses');
    },
    createCourse(course) {
        return axios.post('/course', course);
    }

  }