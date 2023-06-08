<template>
  <div>
    <h3>Invite a Student</h3>
    <p>Enter a person's email address to invite them to join SkillsBridge.</p>
    <form @submit.prevent="sendInvitation">
        <div class="form-group"><input class="form-control" type="text" v-model="emailTemplate.personalizations[0].to[0].email" /><button type="submit">Invite</button></div>
    </form>
  </div>
</template>

<script>
import studentService from '../services/StudentService.js'
export default {
    
    data() {
        return {
            emailTemplate: {
                "personalizations":[
                    {
                        "to":[
                            {
                            "email":"vemali7304@ozatvn.com",
                            // "name":"John Doe"
                            }
                        ]
                    },
                    {
                        "from":{
                            "email":"dscoons@gmail.com",
                            "name":"SkillsBridge"
                        },
                    }
                ],
                "from":{
                    "email":"dscoons@gmail.com",
                    "name":"SkillsBridge"
                },
                "reply_to":{
                    "email":"dscoons@gmail.com",
                    "name":"dscoons@gmail.com"
                },
                "subject":"You've been invited to join a course at SkillsBridge",
                "content":[
                    {
                        "type":"text/html",
                        "value":"<p>Hello from SkillsBridge! You've been invited to join a course: " + this.course.name + "</p><p><a href='http://localhost:8080'>Click here to create a student account</a></p>"
                    }
                ]
            },
            
        }
    },
    props: [ 'course' ],
    methods: {
        sendInvitation() {
            studentService.sendStudentRequest(this.emailTemplate).then(response => {
                console.log(response);
            });
        }
    }
}
</script>

<style>

</style>