package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.Marathon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarathonRepository {
    List<Marathon> getAll();

}
