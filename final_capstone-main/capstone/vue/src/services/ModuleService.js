import axios from 'axios';

export default {
    getModulesForUser() {
      return axios.get('/modules');
    },
    createModule(module) {
        return axios.post('/course/module', module);
    },
    getModules(courseId) {
      return axios.get(`/course/${courseId}/modules`);
    },
    getModule(courseId, moduleId) {
      return axios.get(`/course/${courseId}/module/${moduleId}`);
    }

  }