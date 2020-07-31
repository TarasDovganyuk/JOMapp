package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(exclude = {"task", "user"})
public class Progress {

    public enum TaskStatus{
        PASS, FAIL, PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime started;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @UpdateTimestamp
    private LocalDateTime updated;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "trainee_id", referencedColumnName = "id")
    private User user;
}
