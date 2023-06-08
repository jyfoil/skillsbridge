<template>
    <div class="dashboard">
        <div id="heading-bg">
            <h1 class="dashboard-title">{{ $store.state.user.fullname }} - <span class="course-name">{{ course.name }}</span></h1>
            <router-link class="return-button" tag="button" :to="{ name: 'Teacher Home'}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Dashboard</router-link>
        </div>
        <main id="dashboard-content">
            <div id="content">
                <h2 class="underline">Course Description</h2>
                <section id="description">
                    <textarea class="form-control" type="text" :disabled="descriptionDisabled" v-model="course.description"></textarea>
                    <div class="button-bar"><button v-show="descriptionDisabled" @click="descriptionDisabled = !descriptionDisabled"><img class="icon invert edit" src="../assets/edit.svg" /> Edit</button><button class="small" v-show="!descriptionDisabled" @click="descriptionDisabled = !descriptionDisabled">Save</button></div>
                </section>
                <div class="p-relative"><h2 class="underline">Modules</h2><div class="utilities small hide-mobile"><span @click="gridView = false" :class="{ bold: !gridView}">List View</span> | <span @click="gridView=true" :class="{ bold: gridView}">Grid View</span></div></div>
                <section id="modules" :class="{ grid: gridView}">
                    <!--<router-link class="module-listing" v-for="module in modules" :key="module.id" :to="{name:'teacher-module', params: { courseId: module.courseId, moduleId: module.id }}">{{ module.name }} - <span class="small ">{{ module.description }}</span></router-link>-->
                    <module-listing v-for="module in modules" :key="module.id" :module="module" />
                </section>
                <button @click="hideAddModuleForm = !hideAddModuleForm" class="add"><img class="icon invert" src="../assets/add.svg" /> Add Module</button>
                <div class="accordion" :class="{ hide: hideAddModuleForm }">
                    <div @click="successMsg = ''" v-show="successMsg != ''" class="alert alert-success">{{ successMsg }} </div>
                    <div @click="errorMsg = ''" v-show="errorMsg != ''" class="alert alert-error">{{ errorMsg }} </div>
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
                <h2 class="underline mt-2">Students</h2>
                <section id="students">
                    <div class="student-list-column">
                        <h3 class="mt-0">Current Students</h3>
                        <select class="student-list w-100 form-control" size="8" v-model="selectedStudent">
                            <option v-for="student in filteredStudents" :key="student.id" :value="student">{{student.firstname}} {{student.lastname}}</option>
                        </select>
                        <div class="p-relative">
                            <img v-show="studentSearch != ''" @click="studentSearch=''" class="icon search-icon" src="../assets/close.svg">
                            <input class="form-control filter-students" type="text" v-model="studentSearch" placeholder="Filter Students" />
                        </div>
                    </div>
                    <div class="student-details">
                        <div v-show="removeSuccessMsg != ''" @click="removeSuccessMsg=''" class="alert alert-success">{{ removeSuccessMsg }}</div>
                        <div v-show="removeErrorMsg != ''" @click="removeErrorMsg=''" class="alert alert-success">{{ removeSuccessMsg }}</div>
                        <student-details :student="selectedStudent" :submissions="filteredSubmissions" v-show="selectedStudent.username != ''" />
                        <div class="button-bar mt-1">
                            <button v-show="selectedStudent.username != ''" @click="removeStudent" class="delete red small"><img class="icon invert" src="../assets/delete.svg"> Remove Student</button>
                        </div>
                    </div>
                </section>
                <h3>Add Student to Course</h3>
                <button class="add" @click="addStudentForm"><img class="icon invert" src="../assets/add.svg" /> Add Student</button>
                <div class="accordion student-accordion" :class="{ hide: hideAddStudentForm }">
                    <label for="student-search">Search for a student: </label><div class="p-relative"><img v-show="studentAllSearch != ''" @click="studentAllSearch=''" class="icon search-icon" src="../assets/close.svg"><input id="student-search" ref="studentsearch" type="text" class="mb-1 form-control" v-model="studentAllSearch" placeholder="Search by student name or Email"></div>
                    <div class="aStudentList" ref="show">
                        <div v-if="filteredAllStudents.length > 1">{{ filteredAllStudents.length }} students matched.</div>
                        <div v-else-if="filteredAllStudents.length === 0">{{ filteredAllStudents.length }} students matched.</div>
                        <div v-else v-for="allStudent in filteredAllStudents" :key="allStudent.id" class="flex f-between"><div><strong>Matched Student:</strong> {{allStudent.firstname}} {{allStudent.lastname}} [{{allStudent.username}}]</div><button @click="addStudent" class="small add">Add Student</button></div>
                    </div>
                    <div class="flex border-top extended-results" v-show="!hideExtendedResults">
                        <span>First five results:</span>
                        <div class="capsule" v-for="s in filteredAllStudents.slice(0,5)" :key="s.id" @click="studentAllSearch = s.fullname">{{s.fullname}}</div>
                    </div>
                </div>
            </div>
            <section>
                <submissions-list :submissions="latestSubmissions" />
            </section>
        </main>

    </div>
</template>

<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import studentService from '../services/StudentService.js'
import submissionService from '../services/SubmissionService.js'
import StudentDetails from '../components/StudentDetails.vue'
import ModuleListing from '../components/ModuleListing.vue'
import SubmissionsList from '../components/SubmissionsList.vue'
//import EmailInvitation from '../components/EmailInvitation.vue'

export default {
    data() {
        return {
            modules: [],
            gridView: false,
            descriptionDisabled: true,
            hideAddStudentForm: true,
            hideAddModuleForm: true,
            hideExtendedResults: true,
            studentAllSearch: '',
            studentSearch: '',
            selectedStudent: { fullname:'', username:'' },
            foundStudent: {},
            course: {
                courseId: this.$route.params.courseId,
                name: '',
                description: ''
            },
            successMsg: '',
            errorMsg: '',
            removeSuccessMsg: '',
            removeErrorMsg: '',
            newModule: {
                courseId: this.$route.params.courseId,
            },
            students: [],
            allStudents: [],
            studentIds: [],
            submissions: [],
            filteredSubmissions: []
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
        }),
        studentService.getCourseStudents(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.students = response.data;
                this.updateStudentIdArray();
            }
        }),
        submissionService.getSubmissionsByCourse(this.$route.params.courseId).then(response => {
            if (response.status === 200) {
                this.submissions = response.data;
            }
        })
    },
    components: {
        StudentDetails, ModuleListing, SubmissionsList, //EmailInvitation
    },
    methods: {
        updateStudentIdArray() {
            this.students.forEach(s => {
                this.studentIds.push(s.id);
            });
        },
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
                } else {
                    this.errorMsg = "There was an error creating the module.";
                }
            });
        },
        addStudentForm() {
            this.hideAddStudentForm = !this.hideAddStudentForm;
            if (this.allStudents.length === 0) {
                studentService.getAllStudents().then(response => {
                    if (response.status === 200) {
                        this.allStudents = response.data;
                    }
                })
            }
        },
        addStudent() {
            courseService.addStudent(this.course.courseId, this.foundStudent.id).then(response => {
                if (response.status === 200) {
                    this.students.push(this.foundStudent);
                    this.foundStudent = {};
                    this.studentAllSearch = '';
                    this.hideAddStudentForm = true;
                    this.hideExtendedResults = true;
                    this.updateStudentIdArray();
                }
            });
        },
        removeStudent() {
            courseService.removeStudent(this.course.courseId, this.selectedStudent.id).then(response => {
                if (confirm("Do you really want to remove this student from the course?")) {
                    if (response.status === 204) {
                        this.students.pop(this.selectedStudent);
                        this.selectedStudent = { fullname:'', username:'' };
                        this.removeSuccessMsg = "Student successfully removed from course.";
                        this.updateStudentIdArray();
                    } else {
                        this.removeErrorMsg = "There was an error removing the student.";
                    }
                }
            });
        },
        resetMessages() {
            this.successMsg = '';
            this.errorMsg = '';
            this.removeSuccessMsg = '';
            this.removeErrorMsg = '';
        },
        filteredSubmissionsGetter(id) {
            this.filteredSubmissions = this.submissions.filter(s => s.studentId == id);
        },
    },
    computed: {
        filteredAllStudents() {
            // how to omit students already in course?
            let search = this.studentAllSearch.toLowerCase();
            return this.allStudents.filter(s => {
                let fullname = s.firstname.toLowerCase() + " " + s.lastname.toLowerCase();
                return  ((fullname.includes(search) || s.username.includes(search)) && !this.studentIds.includes(s.id));
            })
        },
        filteredStudents() {
            let search = this.studentSearch.toLowerCase();
            return this.students.filter(s => {
                let fullname = s.firstname.toLowerCase() + " " + s.lastname.toLowerCase();
                return  (fullname.includes(search) || s.username.includes(search));
            })
        },
        latestSubmissions() {
            return this.submissions.map(s => {
                let foundStudent = this.students.find(student => student.id == s.studentId);
                if (foundStudent != undefined) {
                    return {
                        //name
                        //lessonid
                        //submitted_at
                        
                        id: s.submissionId,
                        fullname: foundStudent.fullname,
                        lessonId: s.lessonId,
                        submitted_at: s.submittedAt
                    }
                } else {
                    return {
                        //name
                        //lessonid
                        //submitted_at
                        
                        id: s.submissionId,
                        fullname: "error",
                        lessonId: s.lessonId,
                        submitted_at: s.submittedAt
                    }
                }
            })
        }
    },
    watch: {
        filteredAllStudents: function(s) {
            if (s.length === 1) {
                this.foundStudent = s[0];
            } else {
                this.foundStudent = {};
            }
        },
        selectedStudent: function(s) {
            //this.resetMessages();
            if (s.username != '') {
                this.resetMessages();
            }
            this.filteredSubmissionsGetter(s.id);
        }
    }
}
</script>

<style scoped>
    #students {
        display:flex;
        gap:12px;
        align-content: start;
        padding-bottom:1rem;
        border-bottom:1px solid #CCC;
        margin-bottom:1rem;
    }
    .student-list-column {
        width:25%;
    }
    .student-details  {
        flex-grow:1;
    }
    select.form-control {
        padding: inherit;
        outline:0;
        border-bottom-left-radius: 0;
        border-bottom-right-radius: 0;
    }
    .student-list option {
        padding: 2px 10px;
    }
    .filter-students {
        border-top-left-radius: 0;
        border-top-right-radius: 0;
        border-top:0;
    }
    .icon.search-icon {
        position:absolute;
        right:4px;
        top:3px;
        opacity: 0.5;
    }
    #description textarea {
        min-height:150px;
        resize:none;
    }
    #description textarea:disabled {
        padding:0;
        border:none;
        background:white;
    }
    .extended-results {
        padding-top:12px;
        margin-top:12px;
        align-items:center;
    }
    .grid {
        display:flex;
        flex-wrap:wrap;
        gap:12px;
    }
    .grid .module-listing {
        flex-basis:28%;
        padding:2rem 1rem;
    }
    .icon.edit {
        width:20px;
    }
    .student-accordion {
        padding-top:12px;
        border-radius:4px;
        border-top:8px solid #429CB9;
    }

    @media screen and (max-width: 850px)  {
        .student-list-column {
            width:33%;
        }
    }

    @media screen and (max-width: 590px)  {
        #students {
            flex-direction:column;
        }
        .student-list-column {
            width:100%;
        }
    }
</style>