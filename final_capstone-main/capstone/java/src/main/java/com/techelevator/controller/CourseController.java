package com.techelevator.controller;

import com.techelevator.dao.CourseDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Authority;
import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CourseController {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;

    @PostMapping("/course")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseDTO createCourse(@RequestBody CourseDTO courseDto, Principal principal) {


        // Uses principal to get the currently logged in users name
        // This name is used to get the id of the teacher through a method
        int teacherId = userDao.findIdByUsername(principal.getName());
        // Maps the user courseDTO input into a course object
        // This creates the course for the currently logged in teacher
        Course course = courseDao.mapCourseDtoToCourse(courseDto, teacherId);
        // Uses the course object to create an entry in the course table with all the valid column values
        Course createdCourse = courseDao.createCourse(course);
        // Maps the created course object back into a courseDTO
        // Note: this createdCourse object has the courseId now from the SQL string in the createCourse method
        // The courseDTO is what is returned in the POST body back to the client
        CourseDTO newCourseDto = courseDao.mapCourseToCourseDTO(createdCourse);

        return newCourseDto;

        // We want to return a CourseDto with an id
        // This id might be used later in the frontend
    }

    @DeleteMapping("/course/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseByCourseId(@PathVariable int id) {
        // Method works and deletes course of specified course ID
        // TODO Needs business logic to prevent teachers from deleting courses they don't own

        // The first DAO method makes sure to delete all students in the course before deleting the course
        // This prevents foreign key constraints
        courseDao.deleteStudentsFromCourse(id);
        // Second method deletes the course after all students in the course are removed
        courseDao.deleteCourseByCourseId(id);
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseDao.getCourseByCourseId(id);
    }

//    @GetMapping("/teacher/courses")
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<CourseDTO> getTeacherCourses(Principal principal) {
//        // Similar behavior to create method
//        int teacherId = userDao.findIdByUsername(principal.getName());
//
//        List<Course> teacherCourses = courseDao.getTeacherCoursesByTeacherId(teacherId);
//        List<CourseDTO> teacherCoursesDto = new ArrayList<>();
//        for (Course eachTeacherCourse : teacherCourses) {
//            teacherCoursesDto.add(courseDao.mapCourseToCourseDTO(eachTeacherCourse));
//        }
//
//        return teacherCoursesDto;
//    }


//    @GetMapping("/student/courses")
//    @PreAuthorize("hasRole('USER')")
//    public List<CourseDTO> getStudentCourses(Principal principal) {
//
//        int studentId = userDao.findIdByUsername(principal.getName());
//
//        List<Course> studentCourses = courseDao.getStudentCoursesByStudentId(studentId);
//        List<CourseDTO> studentCoursesDto = new ArrayList<>();
//        for (Course eachCourse : studentCourses) {
//            studentCoursesDto.add(courseDao.mapCourseToCourseDTO(eachCourse));
//        }
//
//        return studentCoursesDto;
//    }

    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<CourseDTO> getCourses(Principal principal) {

        // This method gets the courses of the logged in user
        // This does what the getStudentCourses and getTeacherCourses does but in a single method
        // Those methods currently commented out and kept in case needed later

        int userId = userDao.findIdByUsername(principal.getName());
        User currentLoggedInUser = userDao.getUserById(userId);

        String role = "";
        Set<Authority> auth = currentLoggedInUser.getAuthorities();

        for (Authority eachAuth : auth) {
            String authorityName = eachAuth.getName();
            if (authorityName.equals("ROLE_ADMIN") || (authorityName.equals("ROLE_USER"))) {
                role = authorityName;
            }
        }

        List<Course> courses = (role.equals("ROLE_ADMIN")) ? courseDao.getTeacherCoursesByTeacherId(userId) :
                courseDao.getStudentCoursesByStudentId(userId);
        List<CourseDTO> coursesDto = new ArrayList<>();
        for (Course eachCourse : courses) {
            coursesDto.add(courseDao.mapCourseToCourseDTO(eachCourse));
        }

        return coursesDto;
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getStudents() {

        // Create user object list
        // add all the users who are ROLE_USER (aka students)
        List<User> users = userDao.getStudentsByRoleName("ROLE_USER");
        return users;
    }

    @GetMapping("/course/{courseId}/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getStudentsByCourse(@PathVariable int courseId) {
        return userDao.getStudentsByCourseId(courseId);
    }

    @PostMapping("/course/{courseId}/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addStudentToCourse(@PathVariable int courseId, @PathVariable int studentId) {
        // Path variables used to get the courseId and studentId from the endpoint
        // TODO teachers should only be able to add to courses they created
        courseDao.addStudentToCourse(studentId, courseId);
    }

    @DeleteMapping("/course/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentFromCourse(@PathVariable int courseId, @PathVariable int studentId) {
        // Path variables used to get the courseId and studentId from the endpoint
        // TODO teachers should only be able to delete from courses they created
        courseDao.deleteStudentFromCourse(studentId, courseId);
    }
}
