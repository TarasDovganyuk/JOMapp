package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.service.SprintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class SprintTest {
    private MockMvc mockMvc;
    private SprintService sprintService;
    private MarathonRepository marathonRepository;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    public void setSprintService(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @Autowired
    public void setMarathonRepository(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }


//    @Test
//    public void getSprintsByMarathonIdTest() throws Exception {
//        List<Sprint> expected = sprintService.getSprintsByMarathonId(1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/sprints"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("sprints"))
//                .andExpect(MockMvcResultMatchers.model().attribute("sprints", expected));
//    }

    @Test
    public void addSprintToMarathonTest() {
        Sprint sprint = new Sprint();
        sprint.setTitle("newSprint");
        sprint.setStartDate(LocalDateTime.now());
        sprint.setFinish(LocalDateTime.now());
        sprintService.addSprintToMarathon(sprint, marathonRepository.getMarathonById(2L));
        assertEquals(sprintService.getSprintsByMarathonId(2L).get(0).getTitle(), sprint.getTitle());
    }

    @Test
    public void updateSprintTest() {
        Sprint actual = sprintService.getSprintById(1L);
        actual.setTitle("Updated");
        sprintService.updateSprint(actual);
        assertEquals(sprintService.getSprintById(1L).getTitle(), actual.getTitle());
    }
}
