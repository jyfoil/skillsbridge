package com.techelevator.controller;

import com.techelevator.dao.CourseDao;
import com.techelevator.dao.UserDao;
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

    @GetMapping("/teacher/courses")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CourseDTO> getTeacherCourses(Principal principal) {

        // Similar behavior to create method
        int teacherId = userDao.findIdByUsername(principal.getName());

        List<Course> teacherCourses = courseDao.getTeacherCoursesByTeacherId(teacherId);
        List<CourseDTO> teacherCoursesDto = new ArrayList<>();
        for (Course eachTeacherCourse : teacherCourses) {
            teacherCoursesDto.add(courseDao.mapCourseToCourseDTO(eachTeacherCourse));
        }

        return teacherCoursesDto;
    }

    @GetMapping("/student/courses")
    @PreAuthorize("hasRole('USER')")
    public List<CourseDTO> getStudentCourses(Principal principal) {

        // TODO Currently gives a invalid column error in postman
        // TODO Think I figured it out it has to do with the mapping methods
        // TODO Mapping methods use course_id to get the rowset value
        // TODO need to figure out a way to change sql string to reflect this
        // Seems like something about the query string but it works in pgadmin fine
        int studentId = userDao.findIdByUsername(principal.getName());
        List<Course> courses = courseDao.getStudentCoursesByStudentId(studentId);
        List<CourseDTO> studentCoursesDto = new ArrayList<>();
        for (Course eachCourse : courses) {
            studentCoursesDto.add(courseDao.mapCourseToCourseDTO(eachCourse));
        }

        return studentCoursesDto;
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getStudents() {
        // Not sure if this should return a String which is the full name
        // Or if it should return user object
        // Not sure how it would work on the frontend
        // Principal is not needed to decide role as students are always ROLE_USER

        // Create user object list
        // add all the users who are ROLE_USER (aka students)
        List<User> users = userDao.getStudentsByRoleName("ROLE_USER");
        return users;
    }

    @PostMapping("/course/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addStudentToCourse(@PathVariable int courseId, @PathVariable int studentId) {
        // Path variables used to get the courseId and studentId from the endpoint
        courseDao.addStudentToCourse(studentId, courseId);
    }

    @DeleteMapping("/course/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentFromCourse(@PathVariable int courseId, @PathVariable int studentId) {
        // Path variables used to get the courseId and studentId from the endpoint
        courseDao.deleteStudentFromCourse(studentId, courseId);
    }
}
