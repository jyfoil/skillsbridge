package com.techelevator.dao;

import com.techelevator.model.Lesson;
import com.techelevator.model.LessonDTO;

import java.security.Principal;
import java.util.List;

public interface LessonDao {

    Lesson getLessonById(int lessonId);

    List<Lesson> getLessonsByCourseId(int courseId);

    List<Lesson> getUpcomingLessonsByCourseId(int courseId);

    String getLessonNameFromLessonId(int id);

    int getNumberOfLessonsInCourse(int courseId);

    Lesson createLesson(LessonDTO newLesson);

    List<Lesson> getLessonsByCourseAndModule(int moduleId, int courseId);

    Lesson updateLesson(Lesson lesson);

    void deleteLesson(int id);
}
