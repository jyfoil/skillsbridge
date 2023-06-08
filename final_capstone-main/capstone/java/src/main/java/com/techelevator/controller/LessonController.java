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

    @GetMapping("/course/{id}/lessons")
    public List<Lesson> showLessons(@PathVariable int id) {
        return lessonDao.getLessonsByCourseId(id);
    }

    @GetMapping("/course/{id}/lessons/upcoming")
    public List<Lesson> showUpcomingLessons(@PathVariable int id) {
        return lessonDao.getUpcomingLessonsByCourseId(id);
    }

    @GetMapping("/lesson/{lessonId}")
    public Lesson getLesson(@PathVariable int lessonId) {
        return lessonDao.getLessonById(lessonId);
    }

    @GetMapping("/course/{courseId}/module/{moduleId}/lessons")
    public List<Lesson> getLessonsByCourseAndModule(@PathVariable int courseId, @PathVariable int moduleId) {
        return lessonDao.getLessonsByCourseAndModule(moduleId, courseId);
    }

    @PostMapping("/lesson")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Lesson createLesson(@RequestBody LessonDTO newLesson) {
        return lessonDao.createLesson(newLesson);
    }

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
}
