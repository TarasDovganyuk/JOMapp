package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.JomApplication;
import com.softserve.edu.jom.model.Progress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JomApplication.class)
class ProgressRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProgressRepository progressRepository;

    @Test
    public void testFindByUserIdAndMarathonId() {
        List<Progress> progressList = progressRepository.findByUserIdAndMarathonId(1L, 1L);
        assertEquals(3, progressList.size());

        progressList = progressRepository.findByUserIdAndMarathonId(1L, 2L);
        assertEquals(0, progressList.size());
    }

    @Test
    public void testFindByUserIdAndSprintId() {
        List<Progress> progressList = progressRepository.findByUserIdAndSprintId(1L, 1L);
        assertEquals(2, progressList.size());

        progressList = progressRepository.findByUserIdAndSprintId(1L, 2L);
        assertEquals(1, progressList.size());
        assertEquals(5, progressList.get(0).getId());
    }

    @Test
    public void testGetProgressById() {
        Optional<Progress> foundProgressOptional = progressRepository.findById(1L);
        assertTrue(foundProgressOptional.isPresent());
        Progress foundProgress = foundProgressOptional.get();
        assertEquals(LocalDateTime.of(2020, 07, 05, 0, 0), foundProgress.getStarted());
        assertEquals(LocalDateTime.of(2020, 07, 05, 0, 0), foundProgress.getUpdated());
        assertEquals(Progress.TaskStatus.PENDING, foundProgress.getStatus());
        assertNotNull(foundProgress.getUser());
        assertEquals(1, foundProgress.getUser().getId());
        assertNotNull(foundProgress.getTask());
        assertEquals(1, foundProgress.getTask().getId());
    }

    @Test
    public void testGetProgressWhenNotExist() {
        Optional<Progress> foundProgress = progressRepository.findById(100000L);
        assertFalse(foundProgress.isPresent());
    }
}