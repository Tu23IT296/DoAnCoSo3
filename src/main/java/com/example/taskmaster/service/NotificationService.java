package com.example.taskmaster.service;

import com.example.taskmaster.dto.NotificationDTO;
import com.example.taskmaster.model.Notification;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.NotificationRepository;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Notification> getAllNotifications() {
        User currentUser = userService.getCurrentUser();
        return notificationRepository.findByUser(currentUser);
    }

    public Optional<Notification> getNotificationById(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.filter(n -> n.getUser().getUid().equals(currentUser.getUid()));
    }

    public Notification createNotification(NotificationDTO dto) {
        User currentUser = userService.getCurrentUser();
        Notification notification = new Notification();

        notification.setUser(currentUser);
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        if (dto.getRelatedTaskId() != null) {
            Task relatedTask = taskRepository.findById(dto.getRelatedTaskId())
                    .orElseThrow(() -> new RuntimeException("Task not found with id: " + dto.getRelatedTaskId()));
            if (!relatedTask.getUser().getUid().equals(currentUser.getUid())) {
                throw new IllegalArgumentException("Not authorized to create notification for this task");
            }
            notification.setRelatedTask(relatedTask);
        }

        return notificationRepository.save(notification);
    }

    public void deleteNotification(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent() && notification.get().getUser().getUid().equals(currentUser.getUid())) {
            notificationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Notification not found or not authorized");
        }
    }
}