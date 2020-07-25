package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.ProgressServiceException;
import com.softserve.edu.jom.model.Progress;
import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.repository.ProgressRepository;
import com.softserve.edu.jom.repository.SprintRepository;
import com.softserve.edu.jom.repository.UserRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {
    private ProgressRepository progressRepository;
    private UserRepository userRepository;
    private SprintRepository sprintRepository;

    @Override
    public Progress getProgressById(Long progressId) {
        return progressRepository.getOne(progressId);
    }

    @Override
    public Progress addTaskForStudent(Task task, User user) throws ProgressServiceException {
        try {
            User existedUser = userRepository.getOne(user.getId());
            Validate.notNull(existedUser, "User with id = %s doesn't exist!", user.getId());
            Progress newProgress = new Progress();
            newProgress.setStatus(Progress.TaskStatus.PENDING);
            newProgress.setUser(user);
            Task newTask = new Task();
            Validate.notNull(task.getTitle(), "The task title must not be null");
            newTask.setTitle(task.getTitle());
            if (task.getSprint() != null && task.getSprint().getId() != null) {
                Sprint existedSprint = sprintRepository.getOne(task.getSprint().getId());
                Validate.notNull(existedSprint, "Sprint with id = %s doesn't exist!", task.getSprint().getId());
                newTask.setSprint(existedSprint);
            }
            newProgress.setTask(task);
            return progressRepository.save(newProgress);
        } catch (Exception e) {
            throw new ProgressServiceException(e.getMessage(), e);
        }
    }

    public Progress addOrUpdateProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public boolean setStatus(Progress.TaskStatus taskStatus, Progress progress) {
        progress.setStatus(taskStatus);
        progressRepository.save(progress);
        return true;
    }

    public List<Progress> allProgressByUserIdAndMarathonId(Long userId, Long marathonId) {
        return progressRepository.findByUserIdAndMarathonId(userId, marathonId);
    }

    public List<Progress> allProgressByUserIdAndSprintId(Long userId, Long sprintId) {
        return progressRepository.findByUserIdAndSprintId(userId, sprintId);
    }

    @Autowired
    public void setProgressRepository(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSprintRepository(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }
}
