<template>
    <div id="notifications-column">
        <h3 class="underline">Latest Submissions</h3>
        <div id="notifications-list">
            <div v-for="n in submissions" :key="n.id">
                <span class="sName">{{n.fullname}}</span> in <span class="lName">{{n.lessonName}}</span><br><span class="sDate small">{{n.submitted_at}}</span>
            </div>
            <div>View All Submissions</div>
        </div>
    </div>
</template>

<script>
export default {
    props: [ 'submissions' ],
}
</script>

<style scoped>
#notifications-column h3 {
    line-height:1.6;
}
#notifications-list {
    font-size:0.9rem;
}

#notifications-list div {
    border-bottom:1px solid #CCC;
    padding-bottom:12px;
    margin-bottom:12px;
    color:#888;
}

#notifications-list div:last-child {
    border-bottom:0;
}

.sName, .lName {
    /* font-weight:bold; */
    color:#444;
}

</style>