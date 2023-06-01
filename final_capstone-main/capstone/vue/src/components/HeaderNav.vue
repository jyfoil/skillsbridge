<template>
    <header id="header">
        <router-link :to="{ name: 'home' }" class="header-link">
            <div class="logo-container"><img src="../assets/elearning.png" alt="Logo" class="logo"></div>
        </router-link>
        <h4 class="portal-title">{{ msg }}</h4>
        <div id="nav">
            <a href="#">My Courses</a>
            <div id="notifications" class="icon alert show"><img src="../assets/notifications.svg" /><div id="notification-tray"><ul><li>Notification #1</li><li>Notification #2</li></ul></div></div>
            <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
        </div>
    </header>
</template>

<script>
export default {
    props: [ 'msg' ],
}
</script>

<style>
    header { display:flex; align-items:center; padding-bottom:10px; }
    #notification-tray { display: none; position:absolute; }
    #notifications.show #notification-tray { display:block; right: 0; min-width: 200px; border: 1px solid #CCC; box-shadow: 0 0 10px rgba(0,0,0,0.1); background:white; }
</style>