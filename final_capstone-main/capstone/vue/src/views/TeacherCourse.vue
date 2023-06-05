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
             hideAddModuleForm: true,
             course: {
                 courseId: this.$route.params.courseId,
                 name: '',
                 description: ''
             },
             successMsg: '',
             errorMsg: '',
             newModule: {
                 courseId: this.$route.params.courseId,
             }
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
        }
    }
}
</script>

<style>

</style>