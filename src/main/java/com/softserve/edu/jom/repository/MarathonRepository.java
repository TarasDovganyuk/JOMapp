package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.Marathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonRepository extends JpaRepository<Marathon, Long> {
    Marathon getMarathonById(Long id);

    void deleteMarathonById(Long id);

    Marathon findByTitle(String title);
}
