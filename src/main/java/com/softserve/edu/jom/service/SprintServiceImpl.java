package com.softserve.edu.jom.service;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {

    private SprintRepository sprintRepository;

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public List<Sprint> getSprintsByMarathonId(Long id) {
        return sprintRepository.getSprintsByMarathonId(id);
    }

    @Override
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon) {
        return false; //TODO
    }

    @Override
    public Sprint getSprintById(Long id) {
        return sprintRepository.getSprintById(id);
    }

    @Override
    public boolean updateSprint(Sprint sprint) {

        return false; //TODO
    }
}
