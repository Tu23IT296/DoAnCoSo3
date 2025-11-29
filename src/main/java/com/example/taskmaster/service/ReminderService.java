package com.example.taskmaster.service;

import com.example.taskmaster.dto.ReminderDTO;
import com.example.taskmaster.model.Reminder;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.ReminderRepository;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<ReminderDTO> getAllReminders() {
        User currentUser = userService.getCurrentUser();
        List<Reminder> reminders = reminderRepository.findByUser(currentUser);
        return reminders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<Reminder> getReminderById(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Reminder> reminder = reminderRepository.findById(id);
        return reminder.filter(r -> r.getUser().getUid().equals(currentUser.getUid()));
    }

    public Reminder createReminder(ReminderDTO reminderDTO) {
        User currentUser = userService.getCurrentUser();
        Task task = taskRepository.findById(reminderDTO.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id " + reminderDTO.getTaskId()));

        if (!task.getUser().getUid().equals(currentUser.getUid())) {
            throw new IllegalArgumentException("Not authorized to create reminder for this task");
        }

        Reminder reminder = new Reminder();
        reminder.setTask(task);
        reminder.setUser(currentUser);
        reminder.setReminderTime(reminderDTO.getReminderTime());
        reminder.setMessage(reminderDTO.getMessage());

        return reminderRepository.save(reminder);
    }

    public void deleteReminder(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Reminder> reminder = reminderRepository.findById(id);
        if (reminder.isPresent() && reminder.get().getUser().getUid().equals(currentUser.getUid())) {
            reminderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reminder not found or not authorized");
        }
    }

    public ReminderDTO convertToDTO(Reminder reminder) {
        ReminderDTO dto = new ReminderDTO();
        dto.setTaskId(reminder.getTask().getTaskId());
        dto.setReminderTime(reminder.getReminderTime());
        dto.setMessage(reminder.getMessage());
        return dto;
    }
}