package com.example.taskmaster.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String title;
    private String message;
    private Integer relatedTaskId;
}