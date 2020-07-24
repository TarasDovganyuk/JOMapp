package com.softserve.edu.jom.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate startDate;

    @UpdateTimestamp
    private LocalDate finish;

    @NotNull
    private String title;

    @OneToMany
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "marathon_id", referencedColumnName = "id")
    private Marathon marathon;
}
