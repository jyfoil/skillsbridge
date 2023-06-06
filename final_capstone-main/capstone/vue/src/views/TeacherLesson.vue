<template>
    <div class="dashboard">
        <div id="heading-bg">
            
            <h1>{{ lesson.title }}</h1>
            <h3 class="dashboard-title">{{ course.name }} - <span class="course-name">{{ module.name }}</span></h3>
            <router-link class="return-button" tag="button" :to="{ name: 'teacher-module', props: { courseId: module.courseId }}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Module</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <div class="accordion-container mb-1">
                    <div class="header-accordion" @click="displayLesson = !displayLesson"><h3 class="m-0">Lesson Content</h3><img v-show="!displayLesson" class="icon" src="../assets/arrow_drop_down.svg" /><img v-show="displayLesson" class="icon" src="../assets/arrow_drop_up.svg" /></div>
                    <div class="accordion mt-0 height-limit" v-show="displayLesson">
                        {{ lesson.content }}
                    </div>

                </div>

                <div class="button-bar left"> 
                    <button class="edit" @click="hideEditLessonForm = !hideEditLessonForm"><img class="icon invert" src="../assets/edit.svg" />Edit Lesson</button>
                    <button class="delete red small" @click="deleteLesson"><img class="icon invert" src="../assets/delete.svg" />Delete Lesson</button>
                </div>
                <div class="accordion" :class="{ hide: hideEditLessonForm }">
                    <h3>Edit Lesson</h3>
                    <div>
                        <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} <img class="icon" src="../assets/close.svg"></div>
                        <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} <img class="icon" src="../assets/close.svg"></div>
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
                                <button>Save</button>
                            </div>
                        </form>
                    </div>
                </div>
                <h2 v-if="lesson.has_assignment">Submissions</h2>
                <p v-if="submissions.length === 0">No submissions yet</p>
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
            displayLesson: false,
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
            lesson: {
                courseId: this.$route.params.courseId,
                moduleId: this.$route.params.moduleId,
                title: '',
                content: '',
                resources: '',
                has_assignment: false,
                due_date: '',
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
        })
    },
    methods: {
        updateLesson() {
            lessonService.updateLesson(this.lesson).then(response => {
                if (response.status === 200) {
                    this.lesson = response.data;
                    this.successMsg = "Lesson updated.";
                    //this.$router.push({name: 'teacher-course', params: {courseId:this.lesson.courseId}})
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
        }
    }
}
</script>

<style scoped>
    
</style>