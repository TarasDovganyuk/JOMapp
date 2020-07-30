package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.JomApplication;
import com.softserve.edu.jom.model.Progress;
import com.softserve.edu.jom.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}