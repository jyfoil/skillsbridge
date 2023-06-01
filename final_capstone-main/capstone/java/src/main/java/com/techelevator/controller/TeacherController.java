package com.techelevator.controller;

import com.techelevator.dao.CourseDao;
import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class TeacherController {

    @Autowired
    private CourseDao courseDao;

    @PostMapping("/{teacher_id}/course")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseDTO createCourse(@RequestBody CourseDTO courseDto, @PathVariable int teacher_id) {
        // Currently uses a @PathVariable to link the created course to the specific teacher user ID
        // The frontend might have some way to get this teacher ID from the logged in teacher user login

        // Maps the user courseDTO input into a course object
       Course course = courseDao.mapCourseDtoToCourse(courseDto, teacher_id);
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

    @GetMapping("/{teacher_id}/course")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Course> getTeacherCourses() {
        List<Course> teacherCourses = new ArrayList<>();





        return teacherCourses;
    }
}
