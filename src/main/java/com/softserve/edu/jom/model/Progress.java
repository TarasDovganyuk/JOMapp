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
public class Progress {

    public enum TaskStatus{
        PASS, FAIL, PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate started;

    @NotNull
    private String status;

    @UpdateTimestamp
    private LocalDate updated;

    @ManyToOne
    @JoinColumn(name = "task_fk", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "trainee_fk", referencedColumnName = "id")
    private User user;

    @Enumerated
    private TaskStatus taskStatus;


}
