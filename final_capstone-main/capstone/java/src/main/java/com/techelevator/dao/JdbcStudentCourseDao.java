package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcStudentCourseDao implements StudentCourseDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcStudentCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
