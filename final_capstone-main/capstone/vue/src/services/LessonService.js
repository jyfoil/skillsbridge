import axios from 'axios';

export default {
    createLesson(courseId, moduleId, lesson) {
        return axios.post(`/course/${courseId}/module/${moduleId}/lessons`, lesson);
    },
    getLessons(courseId) {
      return axios.get(`/course/${courseId}/lessons`);
    },
    getLessonsByModule(courseId, moduleId) {
      return axios.get(`/course/${courseId}/module/${moduleId}/lessons`);
    }
  }