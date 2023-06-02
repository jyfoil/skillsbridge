package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    List<User> getStudentsByRoleName(String role);

    boolean create(String username, String firstname, String lastname, String password, String role);
}
