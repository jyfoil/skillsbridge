<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1>{{ lesson.title }}</h1>
            <h3 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h3>
            <router-link class="return-button" tag="button" :to="{ name: 'student-module', props: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Module</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <h2 class="underline">Lesson</h2>
                <div class="html-content" v-html="lesson.content"></div>
                <div v-if="lesson.has_assignment">
                    <h2 class="underline">Assignment</h2>
                    <div class="assignment-details">
                        <p><strong>Instructions:</strong> {{lesson.instructions}} <br />
                        <strong>Due Date:</strong> {{lesson.dueDate}} </p>
                    </div>
                    <h3>Submission</h3>
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} </div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} </div>
                    <form @submit.prevent="submitSubmission" class="flex-column">
                        <div>
                            <wysiwyg v-model="submission.content" />
                            <!-- <textarea id="content" class="form-control" v-model="newLesson.content" placeholder="Lesson Content" required></textarea> -->
                        </div>
                        <div class="button-bar">
                            <button>Submit</button>
                        </div>
                    </form>
                </div>
                <div class="assignment-details mt-2" v-else>There is no Assignment for this Lesson</div>
                
                <router-link class=" small mt-2" tag="button" :to="{ name:'student-module', props: { courseId: module.courseId, moduleId:module.id }}"><img class="icon invert" src="../assets/arrow_back.svg" />Back to Module</router-link>
            </div>


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
            course: {},
            successMsg: '',
            errorMsg: '',
            module: {
                id: this.$route.params.moduleId,
                courseId: this.$route.params.id,
                name: '',
                description: ''
            },
            lesson: {},
            submission: {
                content: '',
                studentId: this.$store.state.user.id,
                lessonId: this.$route.params.lessonId
            }
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
        })
    },
    methods: {
        submitSubmission() {
            submissionService.createSubmission(this.submission).then(response => {
                if (response.status === 200) {
                    this.successMsg = "Assigment successfully submitted.";
                }
            });
        }
    }
}
</script>

<style scoped>
    #dashboard-content {
        grid-template-columns: 1fr;
    }
    .assignment-details {
        padding:0.75rem;
        border:1px solid #CCC;
        background:#EEE;
        border-top:4px solid #429CB9;
        border-radius:4px;
    }
</style>