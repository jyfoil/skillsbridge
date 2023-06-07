<template>
  <div class="dashboard">
    <div id="heading-bg">
      <h1 class="dashboard-title">{{ $store.state.user.fullname }} - <span class="course-name">{{ course.name }}</span></h1>
      <router-link class="return-button" tag="button" :to="{ name: 'Student Home'}"><img class="icon invert" src="../assets/arrow_back.svg" /> Back to Dashboard</router-link>
    </div>
    <main id="dashboard-content">
      <div id="content">
        <h2 class="underline">Course Description</h2>
        <section id="description">
          <p>{{course.description}}</p>
        </section>
        <div class="p-relative"><h2 class="underline">Modules</h2><div class="utilities small"><span @click="gridView = false" :class="{ bold: !gridView}">List View</span> | <span @click="gridView=true" :class="{ bold: gridView}">Grid View</span></div></div>
        <section id="modules" :class="{ grid: gridView}">
          <module-listing v-for="module in modules" :key="module.id" :module="module" />
        </section>
      </div>
      <section>
        <h3 class="assignments">Upcoming Assignments</h3>
        <div class="contact-teacher-wrapper">
            <button class="contact-teacher-button">Contact Teacher</button>
        </div>
      </section>
    </main>
  </div>
</template>


<script>
import courseService from '../services/CourseService.js'
import moduleService from '../services/ModuleService.js'
import ModuleListing from '../components/ModuleListing.vue'
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
            gridView: false
           
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
        ModuleListing
        
    },
    
}
</script>

<style scoped>
    select.form-control {
        padding: inherit;
        outline:0;
        border-bottom-left-radius: 0;
        border-bottom-right-radius: 0;
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
        gap:12px;
    }
    .grid .module-listing {
        flex-basis:33%;
        padding:2rem 1rem;
    }

  
   
</style>