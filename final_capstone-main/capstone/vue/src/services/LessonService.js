import axios from 'axios';

const API_URL = 'http://your-api-url.com/api/';

class LessonService {
  createLesson(data) {
    return axios.post(API_URL + 'lessons', data);
  }

  getLesson(id) {
    return axios.get(API_URL + 'lessons/' + id);
  }
}

export default new LessonService();