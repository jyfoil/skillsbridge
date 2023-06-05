<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ $store.state.user.fullname }} - <span class="course-name">{{ course.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'Teacher Home'}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Dashboard</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <h2>Modules</h2>
                <section id="modules">
                    <div v-for="module in modules" :key="module.id"><router-link :to="{name:'teacher-module', params: { courseId: module.courseId, moduleId: module.id }}">{{ module.name }}</router-link></div>
                </section>
                <button @click="hideAddModuleForm = !hideAddModuleForm" class="add"><img class="icon invert" src="../assets/add.svg" /> Add Module</button>
                <div class="accordion" :class="{ hide: hideAddModuleForm }">
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} <img class="icon" src="../assets/close.svg"></div>
                    <h3>Add New Module</h3>
                    <form @submit.prevent="createModule" class="flex-column">
                        <div>
                            <label for="name">Name:</label>
                            <input id="name" type="text" class="form-control" v-model="newModule.name" placeholder="Module Name" required />
                        </div>
                        <div>
                            <label for="description">Description:</label>
                            <textarea id="description" class="form-control" v-model="newModule.description" placeholder="Module Description" required></textarea>
                        </div>
                        <div class="button-bar">
                            <button v-on:click="cancelForm" type="button">Cancel</button>
                            <button>Submit</button>
                        </div>
                    </form>
                </div>
                <h2>Students</h2>
                <section id="students">
                    <div class="student-list-column">
                        <h3>Current Students</h3>
                        <select class="student-list w-100 form-control" size="8">
                            <option disabled value="">Select a Student</option>
                            <option v-for="student in students" :key="student.id" value="student.id">{{student.firstname}} {{student.lastname}}</option>
                        </select>
                    </div>
                    <div>
                        <h3>Student Details</h3>
                    </div>
                </section>
                <button class="add" @click="addStudentForm"><img class="icon invert" src="../assets/add.svg" /> Add Student</button>
                <div class="accordian" :class="{ hide: hideAddStudentForm }">
                    <input type="text" v-model="studentSearch">
                    <div class="aStudendList">
                        <div v-for="allStudent in allStudents" :key="allStudent.id">{{allStudent.firstname}} {{allStudent.lastname}}</div>
                    </div>
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
export default {
    data() {
        return {
            modules: [],
            hideAddStudentForm: true,
            hideAddModuleForm: true,
            studentSearch: '',
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
            students: [
                {
                id:3,
                firstname:'Studious',
                lastname:'Maximus',
                },
                {
                id:4,
                firstname:'Ferris',
                lastname:'Beuller',
                }
            ],
            allStudents: [
                {
                id:3,
                firstname:'Studious',
                lastname:'Maximus',
                },
                {
                id:4,
                firstname:'Ferris',
                lastname:'Beuller',
                },
                {
                id:1,
                firstname:'Sally',
                lastname:'User',
                }
            ]
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
    methods: {
        cancelForm() {
            this.clearForm();
            this.hideAddModuleForm = true;
        },
        clearForm() {
            //this.hideAddClassForm = true;
            this.newModule.name = '';
            this.newModule.description = '';
        },
        createModule() {
            moduleService.createModule(this.newModule).then(response => {
                if (response.status === 201) {
                    this.successMsg = "Module Created Successfully.";
                    this.modules.push(response.data);
                    this.clearForm();
                    // COMMIT module to store?
                } else {
                    this.errorMsg = "There was an error creating the module.";
                }
            });
        },
        addStudentForm() {
            // todo: grab list of all students from the back end (costly, eventually want to add search function)
        }
    }
}
</script>

<style scoped>
    #students {
        display:flex;
        gap:12px;
        align-content: start;
    }
    .student-list-column {
        width:25%;
    }
    select.form-control {
        padding: inherit;
        outline:1px solid #888;
    }
    .student-list option {
        padding: 2px 10px;
    }
</style>