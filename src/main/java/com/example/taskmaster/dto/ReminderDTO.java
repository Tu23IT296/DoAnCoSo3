package com.example.taskmaster.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderDTO {
    private Integer taskId;
    private LocalDateTime reminderTime;
    private String message;
}