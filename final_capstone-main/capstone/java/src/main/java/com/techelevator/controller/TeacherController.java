package com.techelevator.controller;

import com.techelevator.dao.CourseDao;
import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class TeacherController {

    @Autowired
    private CourseDao courseDao;

    @PostMapping("/{id}/course")
    @PreAuthorize("hasRole('ADMIN')")
    public Course createCourse(@RequestBody CourseDTO courseDto, @PathVariable int id) {

        Course course = courseDao.mapCourseDtoToCourse(courseDto, id);
        return courseDao.createCourse(course);
    }
}
