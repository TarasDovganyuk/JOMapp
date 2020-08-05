package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.MarathonIsNotEmptyException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.repository.MarathonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MarathonServiceImpl implements MarathonService {

    private MarathonRepository marathonRepository;

    @Autowired
    public void setMarathonServiceImpl(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }

    @Override
    public Marathon getMarathonById(Long id) {
        return marathonRepository.getMarathonById(id);
    }

    @Override
    public List<Marathon> getAll() {
        return marathonRepository.findAll();
    }

    @Override
    public void deleteMarathonById(Long id) {
        Marathon marathon = getMarathonById(id);
        if (!marathon.getUsers().isEmpty() || !marathon.getSprints().isEmpty()) {
            log.error(String.format("Marathon deleting error. Marathon id = %d is not empty", id));
            throw new MarathonIsNotEmptyException(String
                .format("Marathon id = %d is not empty. Remove students and sprints from marathon before deleting",
                    id));
        }
        marathonRepository.deleteMarathonById(id);
    }

    @Override
    public Marathon createOrUpdate(Marathon marathon) {
        if (marathon.getId() != null) {
            Marathon existedMarathon = getMarathonById(marathon.getId());
            existedMarathon.setTitle(marathon.getTitle());
            return marathonRepository.save(existedMarathon);
        }
        return marathonRepository.save(marathon);
    }

}
