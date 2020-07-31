package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.ProgressServiceException;
import com.softserve.edu.jom.model.Progress;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@Transactional
@AutoConfigureTestEntityManager
public class ProgressServiceTest {
    private ProgressService progressService;
    private TestEntityManager entityManager;
    private static final String COUNT_QUERY_STRING = "select count(x) from %s x";

    @Autowired
    public void setProgressService(ProgressService progressService) {
        this.progressService = progressService;
    }

    @Autowired
    public void setEntityManager(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void testProgressById() {
        Progress progress = progressService.getProgressById(1L);
        assertEquals(LocalDateTime.of(2020, 07, 05, 0, 0), progress.getStarted());
        assertEquals(LocalDateTime.of(2020, 07, 05, 0, 0), progress.getUpdated());
        assertEquals(Progress.TaskStatus.PENDING, progress.getStatus());
        assertNotNull(progress.getUser());
        assertEquals(1, progress.getUser().getId());
        assertNotNull(progress.getTask());
        assertEquals(1, progress.getTask().getId());
    }

    @Test
    public void testAddTaskToStudent() {
        Long userId = 2L;
        Long taskId = 3L;
        User user = entityManager.find(User.class, userId);
        Task task = entityManager.find(Task.class, taskId);
        long initialProgressCount = count("Progress");
        Progress progress = progressService.addTaskForStudent(task, user);
        long currentProgressCount = count("Progress");
        assertEquals(initialProgressCount + 1, currentProgressCount);
        assertNotNull(progress);
        assertNotNull(progress.getTask());
        assertNotNull(progress.getUser());
        assertEquals(userId, progress.getUser().getId());
        assertEquals(taskId, progress.getTask().getId());
    }

    @Test
    public void testAddTaskToStudentWhenTaskAlreadyAddedToGivenStudent() {
        User user = entityManager.find(User.class, 1L);
        Task task = entityManager.find(Task.class, 1L);
        assertThrows(ProgressServiceException.class, () -> {
            progressService.addTaskForStudent(task, user);
        });
    }

    @Test
    public void testCreateProgress() {
        Progress progress = createNewProgress();
        Progress savedProgress = progressService.addOrUpdateProgress(progress);
        assertNotNull(savedProgress);
        assertNotNull(savedProgress.getId());
        assertEquals(progress.getStarted(), savedProgress.getStarted());
        assertEquals(progress.getUpdated(), savedProgress.getUpdated());
        assertEquals(progress.getStatus(), savedProgress.getStatus());
        assertEquals(progress.getTask().getId(), savedProgress.getTask().getId());
        assertEquals(progress.getUser().getId(), savedProgress.getUser().getId());
    }

    @Test
    public void testUpdateProgress() {
        Progress.TaskStatus newStatus = Progress.TaskStatus.PASS;
        Long progressId = 1L;
        Progress progress = entityManager.find(Progress.class, progressId);
        assertNotEquals(newStatus, progress.getStatus());
        progress.setStatus(newStatus);
        progressService.addOrUpdateProgress(progress);
        Progress savedProgress = entityManager.find(Progress.class, progressId);
        assertEquals(newStatus, savedProgress.getStatus());
    }

    @Test
    public void testSetStatus() {
        Progress.TaskStatus newStatus = Progress.TaskStatus.PASS;
        Long progressId = 1L;
        Progress progress = entityManager.find(Progress.class, progressId);
        assertNotEquals(newStatus, progress.getStatus());
        progress.setStatus(newStatus);
        boolean statusUpdated = progressService.setStatus(newStatus, progress);
        assertTrue(statusUpdated);
        Progress savedProgress = entityManager.find(Progress.class, progressId);
        assertEquals(newStatus, savedProgress.getStatus());
    }

    @Test
    public void testFindByUserIdAndMarathonId() {
        List<Progress> progressList = progressService.allProgressByUserIdAndMarathonId(1L, 1L);
        assertEquals(3, progressList.size());
    }

    @Test
    public void testFindByUserIdAndSprintId() {
        List<Progress> progressList = progressService.allProgressByUserIdAndSprintId(1L, 1L);
        assertEquals(2, progressList.size());
    }

    private Progress createNewProgress() {
        Progress progress = new Progress();
        progress.setStatus(Progress.TaskStatus.PENDING);
        progress.setTask(entityManager.find(Task.class, 3L));
        progress.setUser(entityManager.find(User.class, 2L));
        progress.setStarted(LocalDateTime.of(2020, 7, 31, 17, 30));
        progress.setUpdated(LocalDateTime.of(2020, 8, 01, 9, 30));
        return progress;
    }

    /**
     * Returns total count of given entities
     *
     * @param entityName - entity name
     * @return total items saved in DB
     */
    protected long count(String entityName) {
        return entityManager.getEntityManager().createQuery(String.format(COUNT_QUERY_STRING, entityName), Long.class).getSingleResult();
    }
}