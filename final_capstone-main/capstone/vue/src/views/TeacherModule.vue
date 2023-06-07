<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'teacher-course', params: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Course</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div> 
                    <h2 class="underline">Module Description</h2>
                    <p class="description">{{ module.description }}</p>
                    <div v-if="lessons.length > 0"><div class="small capsule">{{lessons.length}} Lesson(s) in this module.</div></div>
                    <!--<p>Editable name/description by clicking on fields? (use enter key or 'save' button to commit)</p>-->
                    <button @click="deleteModule" :disabled="lessons.length > 0" class="mt-1 delete red"><img class="icon invert" src="../assets/delete.svg" /> Delete Module</button>
                </div>
                <h2 class="underline">Lessons</h2>
                <section id="lessons">
                    <router-link class="lesson-listing" :to="{ name: 'teacher-lesson', params: { courseId:$route.params.courseId, moduleId:$route.params.moduleId, lessonId:lesson.id } }" v-for="lesson in lessons" :key="lesson.id"><h4>{{ lesson.title }}</h4><div v-if="lesson.has_assignment" class="small capsule"> Submissions: {{submissionsPerLesson[lesson.id]}}</div><div v-if="lesson.dueDate != '' && lesson.dueDate != null" class="small flex-grow text-right">Due: <span class="light">{{lesson.dueDate}}</span></div></router-link>
                </section>
                <button @click="hideAddLessonForm = !hideAddLessonForm" class="add"><img class="icon invert" src="../assets/add.svg" /> Add Lesson</button>
                <div class="accordion" :class="{ hide: hideAddLessonForm }">
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} </div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} </div>
                    <h3>Add New Lesson</h3>
                    <form @submit.prevent="createLesson" class="flex-column">
                        <div>
                            <label for="title">Title:</label>
                            <input id="title" type="text" class="form-control" v-model="newLesson.title" placeholder="Lesson Title" required />
                        </div>
                        <div>
                            <label for="content">Content:</label>
                            <wysiwyg v-model="newLesson.content" />
                            <!-- <textarea id="content" class="form-control" v-model="newLesson.content" placeholder="Lesson Content" required></textarea> -->
                        </div>
                        <div>
                            <label for="resources">Resources:</label>
                            <textarea id="resources" class="form-control" v-model="newLesson.resources" placeholder="Lesson Content"></textarea>
                        </div>
                        <div>
                            <label for="hasAssignment">Create Assignment?</label>
                            <input type="checkbox" id="hasAssignment" v-model="newLesson.has_assignment" />
                        </div>
                        <div v-show="newLesson.has_assignment">
                            <label for="dueDate">Due Date</label>
                            <input type="date" id="dueDate" v-model="newLesson.dueDate" />
                        </div>
                        <div v-show="newLesson.has_assignment">
                            <label for="instructions">Instructions</label>
                            <textarea type="textarea" id="instructions" class="form-control" v-model="newLesson.instructions"></textarea>
                        </div>
                        <div class="button-bar">
                            <button v-on:click="cancelForm" type="button">Cancel</button>
                            <button>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
            <section>
                <h3 class="underline">Latest Submissions</h3>
                <!-- <div v-for="(item, index) in submissionsPerLesson" :key="index">{{item}} submissions in Lesson {{index}}</div> -->
                <div class="mb-1" v-for="submission in submissions" :key="submission.submissionId">Submission for {{lessons.find(l => l.id == submission.lessonId).title}} at: <span class="small">{{submission.submittedAt.split('.')[0]}}</span></div>
            </section>
        </main>

    </div>
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'
import submissionService from '../services/SubmissionService.js'
export default {
    data() {
        return {
            lessons: [],
            hideAddLessonForm: true,
            course: {},
            submissions: [],
            module: {
                id: this.$route.params.moduleId,
                courseId: this.$route.params.id,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
            newLesson: {
                courseId: this.$route.params.courseId,
                moduleId: this.$route.params.moduleId,
                title: '',
                content: '',
                resources: '',
                has_assignment: false,
                dueDate: '',
                instructions: ''
             }
        }
    },
    created: function() {
        courseService.getCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.course = response.data;
            }
        }),
        moduleService.getModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.module = response.data;
            }
        }),
        lessonService.getLessonsByModule(this.$route.params.courseId, this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.lessons = response.data;
            }
        }),
        submissionService.getSubmissionsByModule(this.$route.params.moduleId).then(response => {
            if (response.status === 200) {
                this.submissions = response.data;
            }
        })
    },
    components: {
        
    },
    computed: {
        submissionsPerLesson() {
            let temp = [];
            this.submissions.forEach(s => {
                // if (temp.has(s.lessonId)) {
                //     temp.set(s.lessonId, temp.get(s.lessonId) + 1);
                // } else {
                //     temp.set(s.lessonId, 1);
                // }
                if (temp[s.lessonId] == undefined) {
                    temp[s.lessonId] = 1;
                } else {
                    temp[s.lessonId] = temp[s.lessonId] + 1;
                }
            })
            console.log(temp);
            return temp;
        }
    },
    methods: {
        // getNumSubmissions(id) {
        //     let answer = this.submissions.reduce((a, b) => { if (b.lessonId == id) { return a+1; }}, 0);
        //     console.log(answer);
        //     return (answer == undefined) ? 0 : answer;
        // },
        cancelForm() {
            this.clearForm();
            this.hideAddLessonForm = true;
        },
        clearForm() {
            //this.hideAddClassForm = true;
            this.newLesson.title = '';
            this.newLesson.content = '';
            this.newLesson.resources = '';
            this.newLesson.has_assignment = false;
            this.newLesson.dueDate = '';
            this.newLesson.instructions = '';
            this.errorMsg = "";
            this.successMsg = "";
        },
        createLesson() {
            this.errorMsg = "";
            this.successMsg = "";
            if (this.newLesson.has_assignment && this.newLesson.dueDate === '') {
                this.errorMsg = "Must provide a due date.";
                return;
            }
            lessonService.createLesson(this.newLesson).then(response => {
                if (response.status === 201) {
                    this.successMsg = "Lesson Created Successfully.";
                    this.lessons.push(response.data);
                    this.clearForm();
                    // COMMIT lesson to store?
                } else {
                    this.errorMsg = "There was an error creating the lesson.";
                }
            });
        },
        deleteModule() {
            moduleService.deleteModule(this.module.id).then(response => {
                if (response.status === 204) {
                    this.$router.push({name: 'teacher-course', params: { id:this.module.courseId }});
                }
            })
        }
    }
}
</script>

<style scoped>
    @import "~vue-wysiwyg/dist/vueWysiwyg.css";
    .lesson-listing {
        display:flex;
        align-items:center;
        border: 1px solid #CCC;
        padding: 0.75rem 1rem;
        border-radius: 4px;
        margin-bottom: 1rem;
        border-left: 7px solid #429cb9;
        text-decoration:none;
        color:#243e46;
        gap:12px;
    }
    .lesson-listing:hover {
        border-color:#666;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        border-left-color: #17b0e1;
    }
    .lesson-listing > div {
        align-items: center;
    }
    .lesson-listing h4 {
        margin:0;
    }
</style>