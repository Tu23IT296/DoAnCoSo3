package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.TaskStatus;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Task> getAllTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }

    public Optional<Task> getTaskById(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Task> task = taskRepository.findById(id);
        return task.filter(t -> t.getUser().getUid().equals(currentUser.getUid()));
    }

    public Task createTask(TaskDTO taskDTO) {
        User currentUser = userService.getCurrentUser();

        Task task = new Task();
        task.setUser(currentUser);
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setType(taskDTO.getType());
        task.setPriority(taskDTO.getPriority());
        task.setStartTime(taskDTO.getStartTime());
        task.setDueTime(taskDTO.getDueTime());
        task.setStatus(taskDTO.getStatus() != null ? taskDTO.getStatus() : TaskStatus.pending);

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent() && task.get().getUser().getUid().equals(currentUser.getUid())) {
            taskRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Task not found or not authorized");
        }
    }
}