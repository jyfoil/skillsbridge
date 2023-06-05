<template>
    <div class="dashboard">
         <div id="heading-bg">
            <h1 class="dashboard-title">{{ $store.state.user.fullname }} - Student Dashboard</h1>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <h2>My Courses</h2>
                <section id="courses">
                   <course-listing :course="course" v-for="course in courses" :key="course.courseId" />
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
import CourseListing from '../components/CourseListing.vue'
export default {
    data() {
        return {
             courses: [],
        }
    },
    computed: {
        userFirstName() {
            const username = this.$store.state.user.username;
            const firstName = username
            return firstName;
        },
    },
    created: function() {
        this.courses = courseService.getCoursesForUser().then(response => {
            if (response.status === 200) {
                this.courses = response.data;
            }
        });
        //console.log(this.courses);
    },
    components: {
        CourseListing,
    },
    
};
</script>