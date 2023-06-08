<template>
    <div id="notifications-column">
        <h3 class="underline">Latest Submissions</h3>
        <div id="notifications-list">
            <div v-for="n in submissions" :key="n.id">
                <span class="sName">{{n.fullname}}</span><br>on <span class="sDate small">{{n.submitted_at.split('.')[0]}}</span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: [ 'submissions' ],
}
</script>

<style scoped>

</style>