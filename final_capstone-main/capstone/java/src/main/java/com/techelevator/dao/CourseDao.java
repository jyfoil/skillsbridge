package com.techelevator.dao;

import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;

import java.util.List;

public interface CourseDao {

    Course createCourse(Course course);

    void deleteCourseByCourseId(int id);

    Course getCourseByCourseId(int id);

    List<Course> getTeacherCoursesByTeacherId(int id);

    List<Course> getStudentCoursesByStudentId(int id);

    void addStudentToCourse(int studentId, int courseId);

    void deleteStudentFromCourse(int studentId, int courseId);

    void deleteStudentsFromCourse(int courseId);

    Course mapCourseDtoToCourse(CourseDTO courseDTO, int id);

    CourseDTO mapCourseToCourseDTO(Course course);
}
