<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'teacher-course', params: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Course</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div> 
                    <h3>Module Description:</h3>
                    <p>{{ module.description }}</p>
                    <!--<p>Editable name/description by clicking on fields? (use enter key or 'save' button to commit)</p>-->
                    <button @click="deleteModule" :disabled="lessons.length > 0">Delete Module</button>
                </div>
                <h2>Lessons</h2>
                <section id="lessons">
                    <div v-for="lesson in lessons" :key="lesson.id"><h4><router-link :to="{ name: 'teacher-lesson', params: { courseId:$route.params.courseId, moduleId:$route.params.moduleId, lessonId:lesson.id } }">{{ lesson.title }}</router-link></h4></div>
                </section>
                <button @click="hideAddLessonForm = !hideAddLessonForm" class="add"><img class="icon invert" src="../assets/add.svg" /> Add Lesson</button>
                <div class="accordion" :class="{ hide: hideAddLessonForm }">
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <h3>Add New Lesson</h3>
                    <form @submit.prevent="createLesson" class="flex-column">
                        <div>
                            <label for="title">Title:</label>
                            <input id="title" type="text" class="form-control" v-model="newLesson.title" placeholder="Lesson Title" required />
                        </div>
                        <div>
                            <label for="content">Content:</label>
                            <textarea id="content" class="form-control" v-model="newLesson.content" placeholder="Lesson Content" required></textarea>
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
                            <input type="date" id="dueDate" v-model="newLesson.due_date" />
                        </div>
                        <div v-show="newLesson.has_assignment">
                            <label for="instructions">Instructions</label>
                            <textarea type="textarea" id="instructions"></textarea>
                        </div>
                        <div class="button-bar">
                            <button v-on:click="cancelForm" type="button">Cancel</button>
                            <button>Submit</button>
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
            newLesson: {
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
    methods: {
        cancelForm() {
            this.clearForm();
            this.hideAddLessonForm = true;
        },
        clearForm() {
            //this.hideAddClassForm = true;
            this.newLesson.name = '';
            this.newLesson.description = '';
        },
        createLesson() {
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

<style>

</style>