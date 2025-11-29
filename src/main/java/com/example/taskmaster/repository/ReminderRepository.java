package com.example.taskmaster.repository;

import com.example.taskmaster.model.Reminder;
import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
    List<Reminder> findByUser(User user);
}
