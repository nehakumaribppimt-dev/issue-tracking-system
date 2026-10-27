package com.neha.issuetracker.controller;

import com.neha.issuetracker.model.Issue;
import com.neha.issuetracker.repository.IssueRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueRepository repo;

    public IssueController(IssueRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Issue> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Issue one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Issue add(@RequestBody Issue item) {
        return repo.save(item);
    }

    @PutMapping("/{id}")
    public Issue update(@PathVariable Long id, @RequestBody Issue item) {
        Issue old = one(id);
        old.setTitle(item.getTitle());
        old.setDescription(item.getDescription());
        old.setStatus(item.getStatus());
        old.setPriority(item.getPriority());
        old.setProject(item.getProject());
        old.setAssignedTo(item.getAssignedTo());
        return repo.save(old);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }
}
