package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.ProgressServiceException;
import com.softserve.edu.jom.model.Progress;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.model.User;

public interface ProgressService {
    Progress getProgressById(Long progressId);

    Progress addTaskForStudent(Task task, User user) throws ProgressServiceException;
}
