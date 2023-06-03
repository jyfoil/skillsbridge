package com.techelevator.dao;

import com.techelevator.model.Module;
import com.techelevator.model.User;

import java.util.List;

public interface ModuleDao {
    List<Module> getModulesByUser(User user);

    Module getModuleById(int moduleId);

    Module createModule(Module module);

    int deleteModule(int moduleId);
}
