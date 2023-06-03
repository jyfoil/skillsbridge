package com.techelevator.dao;

import com.techelevator.model.Lesson;
import com.techelevator.model.LessonDTO;

import java.security.Principal;
import java.util.List;

public interface LessonDao {
    Lesson getLessonByIdAndCourseId(int lessonId, int courseId);

    public Lesson getLessonById(int lessonId);

    List<Lesson> getLessonsByCourseId(int courseId);

    Lesson createLesson(LessonDTO newLesson);

    List<Lesson> getLessonsByModule(int moduleId, int courseId);
}
