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

    @GetMapping("/courses/{id}/lessons")
    public List<Lesson> showLessons(@Valid @PathVariable int id, Principal p) {
        User user = userDao.findByUsername(p.getName());
        // pass user id?
        return lessonDao.getLessonsByCourseId(id);
    }

    @GetMapping("/courses/{courseId}/lessons/{lessonId}")
    public Lesson getLesson(@PathVariable int courseId, @PathVariable int lessonId) {
        return lessonDao.getLessonById(lessonId, courseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses/{courseId}/lessons")
    public Lesson createLesson(@Valid @PathVariable int courseId, @RequestBody LessonDTO newLesson) {
        newLesson.setCourseId(courseId);
        return lessonDao.createLesson(newLesson);
    }

}
