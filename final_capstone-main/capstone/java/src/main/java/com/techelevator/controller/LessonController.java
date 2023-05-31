package com.techelevator.controller;

import com.techelevator.dao.LessonDao;
import com.techelevator.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/courses/{id}/lessons")
    public List<Lesson> showLessons(@Valid @PathVariable int id, Principal p) {
        System.out.println(p.getName());
        return lessonDao.getLessonsByCourseId(id);
    }

    @GetMapping("/courses/{courseId}/lessons/{lessonId}")
    public Lesson getLesson(@PathVariable int courseId, @PathVariable int lessonId) {
        return lessonDao.getLessonById(lessonId, courseId);
    }

}
