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
                <div>{{ lesson.content }}</div>
                <div class="assignment-details" v-if="lesson.has_assignment">
                    <h2 class="underline">Assignment</h2>
                    <p><strong>Instructions:</strong> {{lesson.instructions}} <br />
                    <strong>Due Date:</strong> {{lesson.dueDate}} </p>
                </div>
                <div class="assignment-details" v-else>No Assignment for this Lesson</div>
                <h3>Submission</h3>
                <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} </div>
                <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} </div>
                <form @submit.prevent="" class="flex-column">
                    <div>
                        <wysiwyg v-model="submission.content" />
                        <!-- <textarea id="content" class="form-control" v-model="newLesson.content" placeholder="Lesson Content" required></textarea> -->
                    </div>
                    <div class="button-bar">
                        <button v-on:click="cancelForm" type="button">Cancel</button>
                        <button>Submit</button>
                    </div>
                </form>
            </div>


        </main>

    </div>
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'
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
                student_id: this.$store.state.user.id,
                lesson_id: this.$route.params.lessonId
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
        cancelForm() {
            return 0;
        }
    }
}
</script>

<style scoped>
    #dashboard-content {
        grid-template-columns: 1fr;
    }
</style>