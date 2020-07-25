package com.softserve.edu.jom.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

    @NotNull
    private String title;

    @UpdateTimestamp
    private LocalDateTime updated;

    @OneToMany(mappedBy = "task")
    private Set<Progress> progress = new HashSet<>(0);

    @ManyToOne
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;
}
