package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TaskTagDTO;
import com.example.taskmaster.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-tags")
@RequiredArgsConstructor
public class TaskTagController {

    private final TaskTagService taskTagService;

    @GetMapping
    public List<TaskTagDTO> getAllTaskTags() {
        return taskTagService.getAllTaskTags();
    }

    @GetMapping("/{taskId}/{tagId}")
    public ResponseEntity<TaskTagDTO> getTaskTagById(@PathVariable Integer taskId, @PathVariable Integer tagId) {
        TaskTagDTO taskTagDTO = taskTagService.getTaskTagById(taskId, tagId);
        return ResponseEntity.ok(taskTagDTO);
    }

    @PostMapping
    public TaskTagDTO createTaskTag(@RequestBody TaskTagDTO taskTagDTO) {
        return taskTagService.createTaskTag(taskTagDTO);
    }

    @DeleteMapping("/{taskId}/{tagId}")
    public ResponseEntity<Void> deleteTaskTag(@PathVariable Integer taskId, @PathVariable Integer tagId) {
        taskTagService.deleteTaskTag(taskId, tagId);
        return ResponseEntity.noContent().build();
    }
}