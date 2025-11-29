package com.example.taskmaster.repository;

import com.example.taskmaster.model.TaskTag;
import com.example.taskmaster.model.TaskTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagId> {

    List<TaskTag> findById_TaskId(Integer taskId);

    List<TaskTag> findById_TagId(Integer tagId);
}
