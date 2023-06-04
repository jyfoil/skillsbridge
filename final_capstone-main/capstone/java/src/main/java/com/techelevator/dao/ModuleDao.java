package com.techelevator.dao;

import com.techelevator.model.Module;
import com.techelevator.model.User;

import java.util.List;

public interface ModuleDao {
    List<Module> getModulesByUser(User user);
//     This method should pass in user Id and not a full user object

    Module getModuleById(int moduleId);

    Module createModule(Module module);

    int deleteModule(int moduleId);
    List<Module> getModulesByCourse(int courseId);
}
