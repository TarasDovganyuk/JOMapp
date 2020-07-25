package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.TaskServiceException;
import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.repository.TaskRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTaskToSprint(Task task, Sprint sprint) throws TaskServiceException {
        Validate.notNull(task.getTitle(), "Title must be not null");
        Validate.notNull(task.getCreated(), "Created date must be not null");

        Task newTask = new Task();
        newTask.setCreated(task.getCreated());
        newTask.setTitle(task.getTitle());
        newTask.setSprint(sprint);
        if (task.getUpdated() != null) {
            newTask.setUpdated(task.getUpdated());
        }
        return taskRepository.save(newTask);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id);
    }
}
