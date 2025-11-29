package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskTagDTO;
import com.example.taskmaster.model.*;
import com.example.taskmaster.repository.TagRepository;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskTagService {

    private final TaskTagRepository taskTagRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<TaskTagDTO> getAllTaskTags() {
        User currentUser = userService.getCurrentUser();
        List<TaskTag> taskTags = taskTagRepository.findAll();
        return taskTags.stream()
                .filter(tt -> tt.getTask().getUser().getUid().equals(currentUser.getUid()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskTagDTO convertToDTO(TaskTag taskTag) {
        return new TaskTagDTO(
                taskTag.getTask().getTaskId(),
                taskTag.getTag().getTagId(),
                taskTag.getTask().getTitle(),
                taskTag.getTag().getName()
        );
    }

    public TaskTagDTO createTaskTag(TaskTagDTO taskTagDTO) {
        User currentUser = userService.getCurrentUser();
        Task task = taskRepository.findById(taskTagDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Tag tag = tagRepository.findById(taskTagDTO.getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        if (!task.getUser().getUid().equals(currentUser.getUid()) || !tag.getUser().getUid().equals(currentUser.getUid())) {
            throw new IllegalArgumentException("Not authorized to create this task-tag");
        }

        TaskTag taskTag = new TaskTag();
        taskTag.setTask(task);
        taskTag.setTag(tag);
        taskTag.setId(new TaskTagId(task.getTaskId(), tag.getTagId()));
        TaskTag savedTaskTag = taskTagRepository.save(taskTag);

        return convertToDTO(savedTaskTag);
    }

    public TaskTagDTO getTaskTagById(Integer taskId, Integer tagId) {
        User currentUser = userService.getCurrentUser();
        TaskTag taskTag = taskTagRepository.findById(new TaskTagId(taskId, tagId))
                .orElseThrow(() -> new RuntimeException("TaskTag not found"));
        if (!taskTag.getTask().getUser().getUid().equals(currentUser.getUid())) {
            throw new IllegalArgumentException("Not authorized");
        }
        return convertToDTO(taskTag);
    }

    public void deleteTaskTag(Integer taskId, Integer tagId) {
        User currentUser = userService.getCurrentUser();
        TaskTagId taskTagId = new TaskTagId(taskId, tagId);
        TaskTag taskTag = taskTagRepository.findById(taskTagId)
                .orElseThrow(() -> new RuntimeException("TaskTag not found"));
        if (!taskTag.getTask().getUser().getUid().equals(currentUser.getUid())) {
            throw new IllegalArgumentException("Not authorized");
        }
        taskTagRepository.deleteById(taskTagId);
    }
}