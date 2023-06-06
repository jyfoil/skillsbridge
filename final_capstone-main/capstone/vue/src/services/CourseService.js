import axios from 'axios';

export default {
    getCoursesForUser() {
      return axios.get('/courses');
    },
    getCourse(id) {
        return axios.get(`/course/${id}`)
    },
    createCourse(course) {
        return axios.post('/course', course);
    }

  }