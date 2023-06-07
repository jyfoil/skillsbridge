<template>
    <div class="dashboard">
        <div id="heading-bg" class="p-relative">
            <h1 class="mb-0">{{ lesson.title }}</h1>
            <h4 v-if="lesson.has_assignment" class="heading-info mt-0">Assignment Due Date: {{ lesson.dueDate }}</h4>
            <h3 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h3>
            <router-link class="return-button" tag="button" :to="{ name: 'teacher-module', props: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Module</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div class="accordion-container mb-1">
                    <div class="header-accordion" @click="displayLesson = !displayLesson"><h3 class="m-0">Lesson Details</h3><img v-show="!displayLesson" class="icon" src="../assets/arrow_drop_down.svg" /><img v-show="displayLesson" class="icon" src="../assets/arrow_drop_up.svg" /></div>
                    <div class="accordion mt-0 height-limit" v-show="displayLesson">
                        <div class="assignment-details" v-if="lesson.has_assignment">
                            <h4 class="mt-0 underline">Assignment</h4>
                            <p><strong>Instructions:</strong> {{lesson.instructions}} <br />
                            <strong>Due Date:</strong> {{lesson.dueDate}} </p>
                        </div>
                        <div class="assignment-details" v-else>No Assignment for this Lesson</div>
                        <h4 class="underline mt-0">Content</h4>
                        <div>{{ lesson.content }}</div>
                    </div>
                </div>

                <div class="button-bar left"> 
                    <button class="edit small" @click="hideEditLessonForm = !hideEditLessonForm"><img class="icon invert" src="../assets/edit.svg" />Edit Lesson</button>
                    <button class="delete red small" @click="deleteLesson" :disabled="submissions.length > 0"><img class="icon invert" src="../assets/delete.svg" />Delete Lesson</button>
                </div>
                <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success mt-1">{{ successMsg }} </div>
                <div class="accordion" :class="{ hide: hideEditLessonForm }">
                    <h3>Edit Lesson</h3>
                    <div>
                        
                        <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} </div>
                        <form @submit.prevent="updateLesson" class="flex-column">
                            <div>
                                <label for="title">Title:</label>
                                <input id="title" type="text" class="form-control" v-model="lesson.title" placeholder="Lesson Title" required />
                            </div>
                            <div>
                                <label for="content">Content:</label>
                                <textarea id="content" class="form-control" v-model="lesson.content" placeholder="Lesson Content" required></textarea>
                            </div>
                            <div>
                                <label for="resources">Resources:</label>
                                <textarea id="resources" class="form-control" v-model="lesson.resources" placeholder="Lesson Content"></textarea>
                            </div>
                            <div>
                                <label for="hasAssignment">Create Assignment?</label>
                                <input type="checkbox" id="hasAssignment" v-model="lesson.has_assignment" />
                            </div>
                            <div v-show="lesson.has_assignment">
                                <label for="dueDate">Due Date</label>
                                <input type="date" id="dueDate" class="form-control" v-model="lesson.dueDate" />
                            </div>
                            <div v-show="lesson.has_assignment">
                                <label for="instructions">Instructions</label>
                                <textarea type="textarea" class="form-control" id="instructions" v-model="lesson.instructions"></textarea>
                            </div>
                            <div class="button-bar">
                                <button v-on:click="cancelForm" type="button">Cancel</button>
                                <button>Save</button>
                            </div>
                        </form>
                    </div>
                </div>
                <h2 v-if="lesson.has_assignment" class="mt-2">Submissions</h2>
                <p v-if="submissions.length === 0">No submissions yet</p>
                <div v-if="gradeSuccessMsg != ''" @click="gradeSuccessMsg = ''" class="alert alert-success">{{gradeSuccessMsg}}</div>
                <div class="submission-listing flex flex-column" v-for="submission in submissions" :key="submission.submittedAt">
                    <div class="flex flex-between"><div>{{studentMap.get(submission.studentId)}}</div><div class="capsule grade" v-if="submission.grade != 0">Grade: {{ submission.grade }}/10</div><div class="capsule dark" v-else>Not Graded</div><div class="small flex-grow text-right">Submitted at: <span class="light">{{submission.submittedAt}}</span></div><button class="small" v-show="showSubmission != submission.submissionId" @click="showSubmission = submission.submissionId">View</button><button v-show="showSubmission === submission.submissionId" class="small muted" @click="showSubmission = 0">Hide</button></div>
                    <div class="submission-content" v-show="showSubmission === submission.submissionId">
                        {{submission.content}}
                    </div>
                    <div v-show="showSubmission === submission.submissionId" class="button-bar"><label for="grade">Grade: </label><input id="grade" v-model="submission.grade" type="text" class=form-control /><button @click="updateGrade(submission)" class="small">Submit Grade</button></div>
                </div>
                <h3 class="mt-2">No submission found for:</h3>
                <div v-for="student in studentsUnsubmitted" :key="student.id">
                    {{ student.fullname }}
                </div>
            </div>
            <section>
                <h3 class="mt-0 underline">Latest Activity</h3>
                <div class="mb-1" v-for="submission in submissions" :key="submission.submissionId">Submission at:<br />{{submission.submittedAt.split('.')[0]}}</div>
            </section>
        </main>

    </div>
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'
import studentService from '../services/StudentService.js'
import submissionService from '../services/SubmissionService.js'
export default {
    data() {
        return {
            showSubmission:0,
            displayLesson: true,
            hideEditLessonForm: true,
            course: {},
            module: {
                id: this.$route.params.moduleId,
                courseId: this.$route.params.id,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
            gradeSuccessMsg: '',
            students: [],
            studentMap: new Map(),
            lesson: {
                courseId: this.$route.params.courseId,
                moduleId: this.$route.params.moduleId,
                title: '',
                content: '',
                resources: '',
                has_assignment: false,
                dueDate: '',
                instructions: ''
            },
            submissions: []
        }
    },
    created: function() {
        lessonService.getLesson(this.$route.params.lessonId).then(response => {
            if (response.status === 200) {
                this.lesson = response.data;
            }
        }),
        moduleService.getModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.module = response.data;
            }
        }),
        courseService.getCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.course = response.data;
            }
        }),
        studentService.getCourseStudents(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.students = response.data;
                response.data.forEach(item => {
                    this.studentMap.set(item.id, item.fullname);
                })
            }
        }),
        submissionService.getSubmissions(this.$route.params.lessonId).then(response => {
            if (response.status === 200) {
                this.submissions = response.data;
            }
        })
    },
    methods: {
        cancelForm() {
            this.hideEditLessonForm = true;
        },
        updateLesson() {
            if (this.lesson.has_assignment && this.lesson.dueDate === '') {
                this.errorMsg = "Must provide a due date.";
                return;
            }
            lessonService.updateLesson(this.lesson).then(response => {
                if (response.status === 200) {
                    this.lesson = response.data;
                    this.hideEditLessonForm = true;
                    this.successMsg = "Lesson updated.";
                } else {
                    this.errorMsg = "There was a problem updating the lesson.";
                }
            });
        },
        deleteLesson() {
            lessonService.deleteLesson(this.lesson.id).then(response => {
                if (response.status === 204) {
                    this.$router.push({name: 'teacher-module', params: { courseId: this.module.courseId, moduleId: this.module.id}})
                }
            });
        },
        updateGrade(submission) {
            submissionService.gradeSubmission(submission).then(response => {
                if (response.status === 200) {
                    this.showSubmission = 0;
                    this.gradeSuccessMsg = "Grade updated."
                }                
            });
        }
    },
    computed: {
        studentsUnsubmitted() {
            const studentsSubmitted = this.submissions.map(s => s.studentId);
            return this.students.filter(item => !studentsSubmitted.includes(item.id));
        }
    }
}
</script>

<style scoped>
    .assignment-details {
        padding-bottom:12px;
    }
    .heading-info {
        font-weight:normal;
    }
    .submission-listing {
        border: 1px solid #CCC;
        padding: 0.75rem 1rem;
        border-radius: 4px;
        margin-bottom: 1rem;
        border-left: 7px solid #429cb9;
        text-decoration:none;
        color:#243e46;
    }
    .submission-listing:hover {
        border-color:#666;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        border-left-color: #17b0e1;
    }
    .submission-listing > div {
        align-items: center;
    }
    .grade {
        color: green;
        background: #e2f7e2;
        border: 1px solid #7cbd7c;
    }
    .dark {
        background:gainsboro;
    }
    button .icon {
        margin-left:-8px;
    }
    .submission-content {
        background:#FEFEFE;
        border:1px solid #EAEAEA;
        padding:10px;
    }
    .button-bar label {
        margin-bottom:0;
    }
    .button-bar input {
        width:40px;
        padding-top: 0.296rem;
        padding-bottom: 0.296rem;
    }
</style>