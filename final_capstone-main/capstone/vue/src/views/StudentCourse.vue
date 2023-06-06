<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ $store.state.user.fullname }} - <span class="course-name">{{ course.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'Student Home'}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Dashboard</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <h2>Modules</h2>
                <section id="modules">
                    <div v-for="module in modules" :key="module.id"><router-link :to="{name:'student-module', params: { courseId: module.courseId, moduleId: module.id }}">{{ module.name }}</router-link></div>
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
export default {
    data() {
        return {
            modules: [],
            course: {
                courseId: this.$route.params.courseId,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
            newModule: {
                courseId: this.$route.params.courseId,
            },
           
        }
    },
    created: function() {
        courseService.getCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.course = response.data;
            }
        }),
        moduleService.getModules(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.modules = response.data;
            }
        })
    },
    components: {
        
    },
    
}
</script>

<style scoped>
   
</style>