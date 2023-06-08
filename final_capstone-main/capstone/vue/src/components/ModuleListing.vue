<template>
      <router-link class="module-listing" :key="module.id" :to="{name:moduleView, params: { courseId: module.courseId, moduleId: module.id }}"><span class="mName">{{ module.name }}</span> <span class="small mDescription">- {{ module.description }}</span></router-link>
</template>

<script>
export default {
    props: [ 'module' ],
    moduleView: 'student-module',
    created: function() {
        this.moduleView = (this.$store.state.user.authorities[0].name === "ROLE_ADMIN") ? 'teacher-module' : 'student-module';
    }
}
</script>
