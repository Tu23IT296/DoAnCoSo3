package com.example.taskmaster.dto;

import com.example.taskmaster.model.TaskStatus;
import com.example.taskmaster.model.TaskType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private TaskType type;
    private Integer priority;
    private LocalDateTime startTime;
    private LocalDateTime dueTime;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}