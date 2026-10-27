package com.neha.issuetracker.controller;

import com.neha.issuetracker.model.Project;
import com.neha.issuetracker.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository repo;

    public ProjectController(ProjectRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Project> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Project one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project add(@RequestBody Project item) {
        return repo.save(item);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project item) {
        Project old = one(id);
        old.setName(item.getName());
        old.setDescription(item.getDescription());
        return repo.save(old);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }
}
