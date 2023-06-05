<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'student-course', params: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Course</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div> 
                    <h3>Module Description:</h3>
                    <p>{{ module.description }}</p>
                </div>
                <h2>Lessons</h2>
                <section id="lessons">
                    <div v-for="lesson in lessons" :key="lesson.id"><h4><router-link :to="{ name: 'teacher-lesson', params: { courseId:$route.params.courseId, moduleId:$route.params.moduleId, lessonId:lesson.id } }">{{ lesson.title }}</router-link></h4></div>
                </section>
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
            lessons: [],
            hideAddLessonForm: true,
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
        })
    },
    components: {
        
    },
    
}
</script>