package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CourseNotFoundException;
import com.techelevator.model.Module;
import com.techelevator.model.ModuleNotFoundException;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcModuleDao implements ModuleDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcModuleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Module> getModulesByUser(User user) {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT module_id, m.course_id, name, description FROM modules m JOIN student_courses sc ON sc" +
                ".course_id = m.course_id WHERE student_id = ?;";
        if (user.getAuthorities().contains("ROLE_ADMIN")) {
            sql = "SELECT module_id, m.course_id, m.name, m.description FROM modules m JOIN courses c ON c.course_id " +
                    "= m.course_id WHERE c.teacher_id = ?;";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        if (results.next()) {
            modules.add(mapRowToModule(results));
        } else {
            throw new ModuleNotFoundException();
        }
        return modules;
    }

    @Override
    public Module getModuleById(int moduleId) {
        Module module = null;
        String sql = "SELECT * FROM modules WHERE module_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, moduleId);

            if (results.next()) {
                module = mapRowToModule(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return module;
    }

    @Override
    public Module createModule(Module module) {
        Module newModule = null;
        System.out.println(module);
        String sql = "INSERT INTO modules (course_id, name, description) VALUES (?, ?, ?) RETURNING module_id;";
        try {
            Integer newModuleId = jdbcTemplate.queryForObject(sql, Integer.class, module.getCourseId(),
                    module.getName(),
                    module.getDescription());
            newModule = getModuleById(newModuleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newModule;
    }

    @Override
    public int deleteModule(int moduleId) {
        String sql = "DELETE FROM modules WHERE module_id = ?;";
        int numberOfRows = 0;
        try {
            numberOfRows = jdbcTemplate.update(sql, moduleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public List<Module> getModulesByCourse(int courseId) {
        String sql = "SELECT * FROM modules WHERE course_id = ?";
        List<Module> modules = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, courseId);
            while (results.next()) {
                modules.add(mapRowToModule(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }

        return modules;
    }

    private Module mapRowToModule(SqlRowSet rs) {
        Module module = new Module();
        module.setId(rs.getInt("module_id"));
        module.setCourseId(rs.getInt("course_id"));
        module.setDescription(rs.getString("description"));
        module.setName(rs.getString("name"));
        return module;
    }
}
