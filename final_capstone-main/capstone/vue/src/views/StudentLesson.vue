<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1>{{ lesson.title }}</h1>
            <h3 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h3>
            <router-link class="return-button" tag="button" :to="{ name: 'student-module', props: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Module</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                
            </div>
            <section>
                <h3>Upcoming Assignments</h3>
            </section>
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
            module: {
                id: this.$route.params.moduleId,
                courseId: this.$route.params.id,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
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

    }
}
</script>

<style>

</style>