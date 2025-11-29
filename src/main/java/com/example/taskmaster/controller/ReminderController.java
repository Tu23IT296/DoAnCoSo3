package com.example.taskmaster.controller;

import com.example.taskmaster.dto.ReminderDTO;
import com.example.taskmaster.model.Reminder;
import com.example.taskmaster.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @GetMapping
    public List<ReminderDTO> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/{reminderId}")
    public ResponseEntity<ReminderDTO> getReminderById(@PathVariable Integer reminderId) {
        Optional<Reminder> reminder = reminderService.getReminderById(reminderId);
        return reminder.map(r -> ResponseEntity.ok(reminderService.convertToDTO(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reminder createReminder(@RequestBody ReminderDTO reminderDTO) {
        return reminderService.createReminder(reminderDTO);
    }

    @DeleteMapping("/{reminderId}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Integer reminderId) {
        reminderService.deleteReminder(reminderId);
        return ResponseEntity.noContent().build();
    }
}