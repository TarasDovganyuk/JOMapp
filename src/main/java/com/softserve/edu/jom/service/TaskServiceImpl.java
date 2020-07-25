package com.softserve.edu.jom.service;

import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@Service
public class TaskServiceImpl implements TaskService{

   private TaskRepository taskRepository;

   @Autowired
   public TaskServiceImpl(TaskRepository taskRepository) {
       this.taskRepository = taskRepository;
   }


    @Override
    public Task addTaskToSprint(Task task, Sprint sprint) {
       task.setSprint(sprint);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id);
    }
}
