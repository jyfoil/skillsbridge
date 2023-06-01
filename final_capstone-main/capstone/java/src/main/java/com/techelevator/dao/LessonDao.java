package com.techelevator.dao;

import com.techelevator.model.Lesson;

import java.security.Principal;
import java.util.List;

public interface LessonDao {
    Lesson getLessonById(int lessonId, int courseId);
    List<Lesson> getLessonsByCourseId(int courseId);
}
