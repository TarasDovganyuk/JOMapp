package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.ToString;
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

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate finish;

    @NotNull
    private String title;

    @ToString.Exclude
    @OneToMany(mappedBy="sprint")
    private List<Task> tasks;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "marathon_id", referencedColumnName = "id")
    private Marathon marathon;
}
