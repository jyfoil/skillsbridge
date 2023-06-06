import axios from 'axios';

export default {
    markLessonComplete(lessonId, userId) {
        return axios.post(`/lesson/${lessonId}/complete`, { userId });
    },
    getCompletedLessonsForUser(userId) {
        return axios.get(`/user/${userId}/completedLessons`);
    },
    getCompletedModulesForUser(userId) {
        return axios.get(`/user/${userId}/completedModules`);
    },
    markModuleComplete(moduleId, userId) {
        return axios.post(`/module/${moduleId}/complete`, { userId });
    },
}