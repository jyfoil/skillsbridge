import axios from 'axios';

const API_URL = 'http://your-api-url.com/api/';

class AssignmentService {
  submitAssignment(data) {
    return axios.post(API_URL + 'assignments', data);
  }
}

export default new AssignmentService();