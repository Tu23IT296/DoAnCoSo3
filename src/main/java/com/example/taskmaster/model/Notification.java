package com.example.taskmaster.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    private String title;
    private String message;
    private Boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "related_task_id")
    private Task relatedTask;

    private LocalDateTime createdAt;
}
