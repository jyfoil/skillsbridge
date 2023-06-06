package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    String getNameByUsername(String username);

    List<User> getStudentsByRoleName(String role);

    List<User> getStudentsByCourseId(int courseId);

    boolean create(String username, String firstname, String lastname, String password, String role);
}
