package com.techelevator.dao;

import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import com.techelevator.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TeacherCourseDao {

    Course createCourse(Course course);
    Course getCourseByCourseId(int id);
    List<Course> getTeacherCoursesByTeacherId(int id);
    void addStudentToCourse(int studentId, int courseId);
    Course mapCourseDtoToCourse(CourseDTO courseDTO, int id);
    CourseDTO mapCourseToCourseDTO(Course course);
}
