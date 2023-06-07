package com.techelevator.controller;

import com.techelevator.dao.LessonDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Lesson;
import com.techelevator.model.LessonDTO;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class LessonController {
    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private UserDao userDao;

//    @GetMapping("/course/{id}/lessons")
//    public List<Lesson> showLessons(@Valid @PathVariable int id, Principal p) {
//        User user = userDao.findByUsername(p.getName());
//        // pass user id?
//        return lessonDao.getLessonsByCourseId(id);
//    }
//
//    @GetMapping("/course/{courseId}/lesson/{lessonId}")
//    // /course/{courseId}/module/{moduleId}/lesson/{lessonId} ?
//    public Lesson getLesson(@PathVariable int courseId, @PathVariable int lessonId) {
//        return lessonDao.getLessonByIdAndCourseId(lessonId, courseId);
//        // The relationship is already set and we just need to get lesson by its module id and
//        // because modules table has a course id we can omit needing the courseId in the endpoint
//    }

    @GetMapping("/lesson/{lessonId}")
    public Lesson getLesson(@PathVariable int lessonId) {
        return lessonDao.getLessonById(lessonId);
    }

    @GetMapping("/course/{courseId}/module/{moduleId}/lessons")
    public List<Lesson> getLessonsByCourseAndModule(@PathVariable int courseId, @PathVariable int moduleId) {
        return lessonDao.getLessonsByCourseAndModule(moduleId, courseId);
    }

//    @PostMapping("/course/{courseId}/module/{moduleId}/lesson")
//    I don't think this long path is needed the relationship between these are all established in the database
    // That path is probably what the frontend path in router of vue will look like

    @PostMapping("/lesson")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Lesson createLesson(@RequestBody LessonDTO newLesson) {
        return lessonDao.createLesson(newLesson);
    }

//    @DeleteMapping("/course/{courseId}//module/{moduleId}/lesson/{id}")
    // These paths above is not needed for the backend but the front end should look like this

    @DeleteMapping("/lesson/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable int id) {
        lessonDao.deleteLesson(id);
    }

    @PutMapping("/module/{moduleId}/lesson/{lessonId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Lesson updateLesson(@PathVariable int moduleId, @PathVariable int lessonId, @RequestBody Lesson lesson) {
        lesson.setModuleId(moduleId);
        lesson.setId(lessonId);
        return lessonDao.updateLesson(lesson);
    }

    @GetMapping("/course/{courseId}/lesson/amount")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public int getNumberOfLessonsInCourse(@PathVariable int courseId) {
        return lessonDao.getNumberOfLessonsInCourse(courseId);
    }

    // return a list of students with the average grade and student id
    // teachers only
}
