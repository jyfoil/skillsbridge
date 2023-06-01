package com.techelevator.dao;

import com.techelevator.model.Course;
import com.techelevator.model.CourseDTO;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface CourseDao {

    Course createCourse(Course course);
    Course getCourse(int id);
    Course mapRowToCourse(SqlRowSet rowSet);
    Course mapCourseDtoToCourse(CourseDTO courseDTO, int id);
    CourseDTO mapCourseToCourseDTO(Course course);
//    int getCourseIdByCourseName();
}
