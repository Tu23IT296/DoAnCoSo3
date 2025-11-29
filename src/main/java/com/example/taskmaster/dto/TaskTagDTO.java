package com.example.taskmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTagDTO {

    private Integer taskId;
    private Integer tagId;
    private String taskTitle;
    private String tagName;
}
