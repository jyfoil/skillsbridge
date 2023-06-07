import axios from 'axios';

export default {
    getSubmissions(lessonId) {
        return axios.get(`/lesson/${lessonId}/submissions`);
    },
    gradeSubmission(submission) {
        return axios.put(`/submission/${submission.submissionId}`, submission);
    },
    getSubmissionsByModule(moduleId) {
        return axios.get(`/module/${moduleId}/submissions`);
    },
    getSubmissionsByCourse(courseId) {
        return axios.get(`/course/${courseId}/submissions`);
    },
    createSubmission(submission) {
        return axios.put(`${submission}`);
    }
}