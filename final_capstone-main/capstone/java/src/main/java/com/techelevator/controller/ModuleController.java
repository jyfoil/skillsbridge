package com.techelevator.controller;

import com.techelevator.dao.ModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.techelevator.model.Module;


import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ModuleController {

    @Autowired
    private ModuleDao moduleDao;


    @PostMapping("/course/module")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Module createModule(@RequestBody Module module, Principal principal) {
        // Returns a JSON body to the user consisting of module_id, course_id, name, description
        // TODO Somewhere on the frontend we might need the list of courses to display the course Id or allow
        //  teachers to get it
        // TODO because the requestbody needs to include the course id, name, and description
        return moduleDao.createModule(module);
    }

    @DeleteMapping("/course/module/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable int id) {
        // TODO Logic to prevent teachers from deleting modules not tied to their course
        moduleDao.deleteModule(id);
    }

    @GetMapping("/course/{id}/modules")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Module> getModulesByClassId(@PathVariable int id) {
        return moduleDao.getModulesByCourse(id);
    }
}
