package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.service.MarathonService;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MarathonTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MarathonService marathonService;

    @Test
    public void getAllMarathonsTest() throws Exception {
        List<Marathon> expected = marathonService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/marathons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("marathons"))
                .andExpect(MockMvcResultMatchers.model().attribute("marathons", expected));
    }

    @Test
    public void createNewMarathonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addMarathon")
        .param("title", "newMarathon"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void updateMarathon() {
        Marathon marathon = marathonService.getMarathonById(2L);
        marathon.setTitle("JavaScript Online Marathon Updated");
        marathonService.createOrUpdate(marathon);
        assertEquals(marathonService.getMarathonById(2L).getTitle(), marathon.getTitle());
    }
}
