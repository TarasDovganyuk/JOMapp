package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
//    void addTaskToSprint(Task task, Sprint sprint);
    Task getTaskById(Long id);
}
