package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.MarathonServiceException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.repository.MarathonRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MarathonServiceImpl implements MarathonService {

    private MarathonRepository marathonRepository;

    @Autowired
    public MarathonServiceImpl(MarathonRepository marathonRepository) {
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
        marathonRepository.deleteMarathonById(id);
    }

    @Override
    public Marathon createOrUpdate(Marathon marathon)  {
        System.out.println(marathon);
        if (marathon.getId() != null) {

            Marathon existedMarathon = getMarathonById(marathon.getId());
            existedMarathon.setTitle(marathon.getTitle());
            return marathonRepository.save(existedMarathon);
//            Optional<Marathon> marathonOptional = marathonRepository.findById(marathon.getId());
//
//            if (marathonOptional.isPresent()) {
//                Marathon newMarathon = marathonOptional.get();
//                newMarathon.setTitle(marathon.getTitle());
//                return marathonRepository.save(newMarathon);
//            }
        }

        return marathonRepository.save(marathon);
    }

}
