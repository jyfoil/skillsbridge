import axios from 'axios';

export default {
    gradeSubmission(submission) {
        return axios.put(`/submission/${submission.submission_id}`, submission);
    }
}