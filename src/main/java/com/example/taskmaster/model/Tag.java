package com.example.taskmaster.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    private String name;
    private String color;

    private LocalDateTime createdAt;
}
