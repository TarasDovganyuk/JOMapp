package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime finish;

    @NotNull
    private String title;

    @ToString.Exclude
    @OneToMany(mappedBy = "sprint")
    private Set<Task> tasks = new HashSet<>(0);

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "marathon_id", referencedColumnName = "id")
    private Marathon marathon;
}
