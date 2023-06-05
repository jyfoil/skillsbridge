<template>
    <div class="dashboard">
        <div id="heading-bg">
            
            <h1>{{ lesson.title }}</h1>
            <h3 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h3>
            <router-link class="return-button" tag="button" :to="{ name: 'teacher-module', props: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Module</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div> 

                </div>
                <h2>Edit Lesson</h2>
                <div>
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <h3>Edit Lesson</h3>
                    <form @submit.prevent="createLesson" class="flex-column">
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
                            <input type="datetime" id="dueDate" v-model="lesson.due_date" />
                        </div>
                        <div v-show="lesson.has_assignment">
                            <label for="instructions">Instructions</label>
                            <textarea type="textarea" id="instructions"></textarea>
                        </div>
                        <div class="button-bar">
                            <button>Save</button>
                        </div>
                    </form>
                </div>
            </div>
            <section>
                <h3>Latest Activity</h3>
            </section>
        </main>

    </div>
</template>

<script>
//import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import lessonService from '../services/LessonService.js'
export default {
    data() {
        return {
            course: {},
            module: {
                id: this.$route.params.moduleId,
                courseId: this.$route.params.id,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
            lesson: {
                courseId: this.$route.params.courseId,
                moduleId: this.$route.params.moduleId,
                title: '',
                content: '',
                resources: '',
                has_assignment: false,
                due_date: '',
                instructions: ''
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
        })
    },
    methods: {

    }
}
</script>

<style>

</style>