import axios from 'axios';

export default {
    createLessonOld(courseId, moduleId, lesson) {
        return axios.post(`/course/${courseId}/module/${moduleId}/lessons`, lesson);
    },
    createLesson(lesson) {
      return axios.post(`/lesson`, lesson);
    },
    getUpcomingLessons(courseId) {
      return axios.get(`/course/${courseId}/lessons/upcoming`);
    },
    getLessonsByModule(courseId, moduleId) {
      return axios.get(`/course/${courseId}/module/${moduleId}/lessons`);
    },
    getLesson(lessonId) {
      return axios.get(`/lesson/${lessonId}`);
    },
    updateLesson(lesson) {
      return axios.put(`/module/${lesson.moduleId}/lesson/${lesson.id}`, lesson);
    },
    deleteLesson(lessonId) {
      return axios.delete(`/lesson/${lessonId}`);
    }
  }