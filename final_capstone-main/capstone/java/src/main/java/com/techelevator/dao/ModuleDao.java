package com.techelevator.dao;

import com.techelevator.model.Module;
import com.techelevator.model.User;

import java.util.List;

public interface ModuleDao {
    public List<Module> getModulesByUser(User user);
    public Module getModuleById(int moduleId);
    public Module createModule(Module module);
    public int deleteModule(int moduleId);
}
