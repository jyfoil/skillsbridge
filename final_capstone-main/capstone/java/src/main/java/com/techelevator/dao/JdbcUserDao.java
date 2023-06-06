package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.UserNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

    @Override
    public String getNameByUsername(String username) {
        String fullName;
        String sql = "SELECT firstname || ' ' || lastname AS fullname " +
                "FROM users " + "" +
                "WHERE username = ?";

        try {
            fullName = jdbcTemplate.queryForObject(sql, String.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return fullName;
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<User> getStudentsByRoleName(String role) {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE role = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, role);

            while (results.next()) {
                users.add(mapRowToUser(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return users;
    }

    @Override
    public List<User> getStudentsByCourseId(int courseId) {
        List<User> students = new ArrayList<>();
        String sql = "SELECT users.user_id, users.firstname, users.lastname, users.username, users.password_hash, " +
                "users.role " +
                "FROM users " +
                "JOIN student_courses ON users.user_id = student_courses.student_id " +
                "WHERE student_courses.course_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);

            while (results.next()) {
                students.add(mapRowToUser(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return students;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String firstname, String lastname, String password, String role) {
        String insertUserSql = "insert into users (username,firstname,lastname,password_hash,role) values (?,?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, firstname, lastname, password_hash, ssRole) == 1;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }
}
