import axios from 'axios';

export default {
    createLessonOld(courseId, moduleId, lesson) {
        return axios.post(`/course/${courseId}/module/${moduleId}/lessons`, lesson);
    },
    createLesson(lesson) {
      return axios.post(`/lesson`, lesson);
  },
    getLessons(courseId) {
      return axios.get(`/course/${courseId}/lessons`);
    },
    getLessonsByModule(courseId, moduleId) {
      return axios.get(`/course/${courseId}/module/${moduleId}/lessons`);
    },
    getLesson(lessonId) {
      return axios.get(`/lesson/${lessonId}`);
    }
  }